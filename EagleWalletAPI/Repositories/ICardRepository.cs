using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Repositories
{
    public interface ICardRepository
    {
        Task<CreditCard> AddUserCard(CreditCard card);
        Task<bool> DeleteCard(int cardId);
        Task<List<CreditCard>> GetUserCards(int userId);
    
    }
}
