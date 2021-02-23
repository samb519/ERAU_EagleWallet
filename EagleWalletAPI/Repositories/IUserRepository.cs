using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using EagleWalletAPI.Models;

namespace EagleWalletAPI.Repositories
{
    public interface IUserRepository
    {
        Task<User> GetUser(string username);
        Task<User> GetUser(int userId);
        Task<User> AddUser(User user);
        Task<User> DeleteUser(string username);
    }
}
