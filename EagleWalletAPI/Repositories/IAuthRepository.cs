using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Repositories
{
    public interface IAuthRepository
    {
        Task<Tuple<int, string>> Register(UserCreateDto user);
        Task<User> Login(string username, string password);
        Task<bool> ValidateUser(int userId);
    }
}
