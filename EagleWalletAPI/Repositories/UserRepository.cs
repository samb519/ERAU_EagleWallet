using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using EagleWalletAPI.Models;

namespace EagleWalletAPI.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly DatabaseContext context;

        public UserRepository(DatabaseContext context)
        {
            this.context = context ?? throw new ArgumentNullException(nameof(context));
        }

        public async Task<User> AddUser(User user)
        {
            await context.Users.AddAsync(user);
            return user;
        }

        public Task<User> DeleteUser(string username)
        {
            return Task.Run(() => {
                var existing = context.Users.FirstOrDefault(u => u.Username == username);
                if (existing == null)
                    return null;
                context.Users.Remove(existing);
                return existing;
            });
        }

        public Task<User> GetUser(string username)
        {
            return Task.Run(() => {
                var existing = context.Users.FirstOrDefault(u => u.Username == username);
                if (existing == null)
                    return null;
                return existing;
            });
        }
        public Task<User> GetUser(int id)
        {
            return Task.Run(() => {
                var existing = context.Users.FirstOrDefault(u => u.Id == id);
                if (existing == null)
                    return null;
                return existing;
            });
        }
    }
}
