using EagleWalletAPI.DTO;
using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using EagleWalletAPI.Repositories;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using MimeKit;
using MailKit.Net.Smtp;
using MailKit.Security;
using System.IO;

namespace EagleWalletAPI.Controllers
{
    [Route("api/[controller]")]
    public class AuthController : Controller
    {

        private readonly IAuthRepository repository;

        private static readonly char[] INVALID_CHARACTERS = new char[14] { ';', '$', '%', '{', '}', '>', '<', '?', '#', '(', ')', ']', '[', '`' };

        public AuthController(IAuthRepository repository)
        {
            this.repository = repository ?? throw new ArgumentNullException(nameof(repository));
        }

        /// <summary>
        /// Registers the user for a DinDin account.
        /// </summary>
        /// <param name="dto">The user to create.</param>
        /// <returns>The details of the created user.</returns>
        /// <response code="201">Indicates that the new user was successfully created.</response>
        /// <response code="400">Indicates that improper JSON was supplied.</response>
        /// <response code="500">Indicates that the new user failed to save to the database.</response>
        [HttpPost("register")]
        [ProducesResponseType(200, Type = typeof(User))]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(500, Type = typeof(string))]
        [Produces("application/json")]
        public async Task<IActionResult> Register([FromBody] UserCreateDto dto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            foreach (char c in INVALID_CHARACTERS)
            {
                if (dto.Email.Contains(c))
                    ModelState.AddModelError("Email", "Invalid character \"" + c + "\"");
            }

            if (!dto.Password.Any(char.IsUpper))
                ModelState.AddModelError("Password", "Upper case character required");

            if (!dto.Password.Any(char.IsLower))
                ModelState.AddModelError("Password", "Lower case character required");

            if (!dto.Password.Any(char.IsDigit))
                ModelState.AddModelError("Password", "Number required");

            if (!dto.Password.Any(c => !char.IsLetterOrDigit(c)))
                ModelState.AddModelError("Password", "Special character required");


            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var result = await repository.Register(dto);

            if (result.Item1 == -1 && result.Item2.Contains("StudentID"))
                ModelState.AddModelError("StudentID", "StudentID already taken");

            if (result.Item1 == -1 && result.Item2.Contains("email"))
                ModelState.AddModelError("Email", "Email is already associated with an account.");

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var userToCreate = new User()
            {
                Email = dto.Email,
                Id = result.Item1
            };

            SendAuthenticationEmail(result.Item1, dto.Email);

            return Ok(userToCreate);
        }

        private void SendAuthenticationEmail(int userId, string email)
        {
            string emailText = System.IO.File.ReadAllText(Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Controllers", "verificationEmail.html"));
            emailText = emailText.Replace("<idGoesHere>", userId.ToString());
            var mailMessage = new MimeMessage();
            mailMessage.From.Add(new MailboxAddress("noreply", "noreply@wise-net.xyz"));
            mailMessage.To.Add(new MailboxAddress(email.Split("@")[0], email));
            mailMessage.Subject = "subject";
            mailMessage.Body = new TextPart("html")
            {
                Text = emailText 
            };

            using (var smtpClient = new SmtpClient())
            {
                smtpClient.Connect("mail.wise-net.xyz", 25, SecureSocketOptions.StartTls);
                smtpClient.Authenticate("noreply@wise-net.xyz", System.Environment.GetEnvironmentVariable("EmailPassword", EnvironmentVariableTarget.Process));
                smtpClient.Send(mailMessage);
                smtpClient.Disconnect(true);
            }
        }

        /// <summary>
        /// Logs in the user with the credentials specified.
        /// </summary>
        /// <param name="dto">The user's login credentials.</param>
        /// <returns>The details of the user and their authorization information.</returns>
        /// <response code="200">Indicates that the login was successful.</response>
        /// <response code="400">Indicates that improper JSON was supplied.</response>
        /// <response code="401">Indicates that the login was not successful.</response>
        [HttpPost("login")]
        [Produces("application/json")]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(401)]
        [ProducesResponseType(200, Type = typeof(User))]
        public async Task<IActionResult> Login([FromBody] UserLoginDto dto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var user = await repository.Login(dto.Email, dto.Password);

            if (user == null)
                return Unauthorized();

            return Ok(user);
        }


        /// <summary>
        /// Verifies the supplied users email.
        /// </summary>
        /// <param name="dto">The user's login credentials.</param>
        /// <returns>The details of the user and their authorization information.</returns>
        /// <response code="200">Indicates that the login was successful.</response>
        /// <response code="400">Indicates that improper JSON was supplied.</response>
        /// <response code="401">Indicates that the login was not successful.</response>
        [HttpGet("validation/{id}")]
        [Produces("application/json")]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(401)]
        [ProducesResponseType(200, Type = typeof(User))]
        public async Task<IActionResult> ValidateUser(int id)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if(await repository.ValidateUser(id))
                return Ok();
            else
                return Unauthorized();
        }
    }
}
