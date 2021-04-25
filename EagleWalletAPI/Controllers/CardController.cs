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
    public class CardController : Controller
    {

        private readonly ICardRepository repository;

        public CardController(ICardRepository repository)
        {
            this.repository = repository ?? throw new ArgumentNullException(nameof(repository));
        }

        /// <summary>
        /// Adds the credit card into the database.
        /// </summary>
        /// <param name="dto">The card to create.</param>
        /// <returns>The details of the created card.</returns>
        /// <response code="201">Indicates that the card was successfully created.</response>
        /// <response code="400">Indicates that improper JSON was supplied.</response>
        /// <response code="500">Indicates that the new card failed to save to the database.</response>
        [HttpPost("create")]
        [ProducesResponseType(200, Type = typeof(CreditCard))]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(500, Type = typeof(string))]
        [Produces("application/json")]
        public async Task<IActionResult> AddCard([FromBody] CreditCard dto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var result = await repository.AddUserCard(dto);
            return Ok(result);
        }

        /// <summary>
        /// Gets all the credit cards for the supplied user.
        /// </summary>
        /// <param name="userId">The user id to retrieve cards for.</param>
        /// <returns>The details of the user and their authorization information.</returns>
        /// <response code="200">Indicates that the login was successful.</response>
        /// <response code="400">Indicates that improper JSON was supplied.</response>
        /// <response code="204">Indicates that the user has no cards.</response>
        [HttpPost("getCards/{userId}")]
        [Produces("application/json")]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(204)]
        [ProducesResponseType(200, Type = typeof(List<CreditCard>))]
        public async Task<IActionResult> GetCards(int userId)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var result = await repository.GetUserCards(userId);
            if(result == null || result.Count == 0)
                return NoContent();
            return Ok(result);
        }


        /// <summary>
        /// Deletes the credit card with the supplied id.
        /// </summary>
        /// <param name="cardId">The id of the card to delete.</param>
        /// <response code="200">Indicates that the deletion was successful.</response>
        /// <response code="400">Indicates that card was not deleted.</response>
        [HttpPost("delete/{cardId}")]
        [Produces("application/json")]
        [ProducesResponseType(400, Type=typeof(string))]
        [ProducesResponseType(200, Type=typeof(string))]
        public async Task<IActionResult> DeleteCard(int cardId)
        {
            if(await repository.DeleteCard(cardId))
                return Ok("Card Deleted!");
            else
                return BadRequest("Card not found or unable to be deleted!");
        }
    }
}
