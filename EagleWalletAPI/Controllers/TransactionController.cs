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
using Microsoft.AspNetCore.Http;

namespace EagleWalletAPI.Controllers
{
    [Route("api/[controller]")]
    public class TransactionController : Controller
    {

        private readonly ITransactionRepository repository;

        public TransactionController(ITransactionRepository repository)
        {
            this.repository = repository ?? throw new ArgumentNullException(nameof(repository));
        }

        /// <summary>
        /// Gets the balances of all accounts for the specified user.
        /// </summary>
        /// <param name="userId">The userId to lookup the balance for.</param>
        /// <returns>The balances for each fund type for the user.</returns>
        /// <response code="200">Indicates that the request was successful.</response>
        /// <response code="400">Indicates that the user was not found.</response>
        /// <response code="500">Indicates that the server had an issue.</response>
        [HttpGet("balances/{userId}")]
        [ProducesResponseType(200, Type = typeof(User))]
        [ProducesResponseType(400, Type=typeof(String))]
        [ProducesResponseType(500, Type = typeof(String))]
        [Produces("application/json")]
        public async Task<IActionResult> GetBalances(int userId)
        {
            try{
                var result = await repository.GetUserBalances(userId);
                if(result == null)
                    return BadRequest("User does not exist!");
                else
                    return Ok(result);
            }catch (Exception e){
                return StatusCode(StatusCodes.Status500InternalServerError, e.Message + "-" + e.StackTrace);
            }
        }

        /// <summary>
        /// Modifies the balances of a user's account by the specified amounts.
        /// </summary>
        /// <param name="dto">The amount to modify each accounts balance by.</param>
        /// <returns>The result of the transaction in the form of the new balance.</returns>
        /// <response code="200">Indicates that the transaction was successful.</response>
        /// <response code="400">Indicates that the transaction couldnt be completed.</response>
        /// <response code="500">Indicates that the server couldn't complete the transaction.</response>
        [HttpPost("modifyBalances")]
        [Produces("application/json")]
        [ProducesResponseType(400, Type = typeof(Microsoft.AspNetCore.Mvc.ModelBinding.ModelStateDictionary))]
        [ProducesResponseType(400, Type=typeof(String))]
        [ProducesResponseType(200, Type = typeof(User))]
        public async Task<IActionResult> ModifyBalances([FromBody] UserBalances dto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);
            
            try{
                var result = await repository.ModifyBalances(dto);
                if(result == null)
                    return NotFound("User does not exist!");
                else
                    return Ok(result);
            }catch (Exception e){
                return BadRequest(e.Message);
            }
        }
    }
}
