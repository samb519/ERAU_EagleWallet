using AutoMapper;
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

namespace EagleWalletAPI.Controllers
{
    [Route("api/[controller]")]
    public class AuthController : Controller
    {

        private readonly IAuthRepository repository;
        private readonly IConfiguration config;
        private readonly IMapper mapper;

        private static readonly char[] INVALID_CHARACTERS = new char[14] { ';', '$', '%', '{', '}', '>', '<', '?', '#', '(', ')', ']', '[', '`' };

        public AuthController(IAuthRepository repository, IConfiguration config, IMapper mapper)
        {
            this.repository = repository ?? throw new ArgumentNullException(nameof(repository));
            this.config = config ?? throw new ArgumentNullException(nameof(config));

            this.repository = repository ?? throw new ArgumentNullException(nameof(repository));
            this.mapper = mapper ?? throw new ArgumentNullException(nameof(mapper));
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
        [ProducesResponseType(201, Type = typeof(UserDetailedDto))]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(500, Type = typeof(string))]
        [Produces("application/json")]
        public async Task<IActionResult> Register([FromBody] UserCreateDto dto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (!string.IsNullOrEmpty(dto.Username))
                dto.Username = dto.Username.ToLower();

            if (await repository.UserExists(dto.Username))
                ModelState.AddModelError("Username", "Username already taken");

            if (await repository.UserExists(dto.Email))
                ModelState.AddModelError("Email", "Email is already associated with an account.");

            foreach (char c in INVALID_CHARACTERS)
            {
                if (dto.Username.Contains(c))
                    ModelState.AddModelError("Username", "Invalid character \"" + c + "\"");
                if (dto.Password.Contains(c))
                    ModelState.AddModelError("Password", "Invalid character \"" + c + "\"");
                if (dto.Email.Contains(c))
                    ModelState.AddModelError("Email", "Invalid character \"" + c + "\"");
            }

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var userToCreate = new User()
            {
                Username = dto.Username,
                Email = dto.Email
            };

            var createUser = await repository.Register(userToCreate, dto.Password);
            if (await writer.SaveAsync())
            {
                var result = mapper.Map<UserDetailedDto>(createUser);
                return StatusCode(201, result);
            }
            return StatusCode(500, "Failed to save to database.");
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
        [ProducesResponseType(200, Type = typeof(UserLoginSuccessDto))]
        public async Task<IActionResult> Login([FromBody] UserLoginDto dto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var user = await repository.Login(dto.Username, dto.Password);

            if (user == null)
                return Unauthorized();

            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(config.GetSection("AppSettings:Token").Value);

            var tokenDescriptor = new SecurityTokenDescriptor()
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                    new Claim(ClaimTypes.Name, user.Username)
                }),
                Expires = DateTime.Now.AddDays(1),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha512Signature)
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);
            var tokenString = tokenHandler.WriteToken(token);


            var result = mapper.Map<UserLoginSuccessDto>(user);
            result.LoginToken = tokenString;
            return Ok(result);
        }
    }
}
