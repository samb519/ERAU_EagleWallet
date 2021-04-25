using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Repositories
{
    public interface ITransactionRepository
    {
        Task<UserBalances> GetUserBalances(int userId);
        Task<UserBalances> ModifyBalances(UserBalances balances);
        Task<List<TransactionHistory>> GetTransactionHistory(int userId);
    }
}
