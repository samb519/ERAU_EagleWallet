﻿using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EagleWalletAPI.Repositories
{
    public class AuthRepository : IAuthRepository
    {
        private readonly IDbConnectionProvider context;

        public AuthRepository(IDbConnectionProvider context)
        {
            this.context = context ?? throw new ArgumentNullException(nameof(context));
        }

        public async Task<User> Login(string username, string password)
        {
            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspGetUserLogin"))
            {
                cmd.Parameters.AddWithValue("@Username", username);
                using (var reader = await cmd.ExecuteReaderAsync())
                {
                    string Username, Email;
                    byte[] PasswordHash, PasswordSalt;
                    while (reader.Read())
                    {
                        Username = reader["Username"].ToString();
                        Email = reader["Email"].ToString();
                        PasswordHash = (byte[])reader["PasswordHash"];
                        PasswordSalt = (byte[])reader["PasswordSalt"];

                        if (VerifyPasswordHash(password, PasswordHash, PasswordSalt))
                        {
                            return new User()
                            {
                                Id = int.Parse(reader["Id"].ToString()),
                                Email = Email,
                                Username = Username
                            };
                        } 
                        else
                        {
                            return null;
                        }
                    }

                }
            }
            return null;
        }

        private bool VerifyPasswordHash(string password, byte[] passwordHash, byte[] passwordSalt)
        {
            using (var hmac = new System.Security.Cryptography.HMACSHA512(passwordSalt))
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));

                if (computedHash.Length != passwordHash.Length)
                    return false;

                for (int i = 0; i < computedHash.Length; i++)
                    if (computedHash[i] != passwordHash[i]) return false;

                return true;
            }
        }

        public async Task<Tuple<int, string>> Register(UserCreateDto user)
        {

            CreatePasswordHash(user.Password, out byte[] hash, out byte[] salt);

            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspCreateUser"))
            {
                cmd.Parameters.AddWithValue("@Username", user.Username);
                cmd.Parameters.AddWithValue("@Email", user.Email);
                cmd.Parameters.AddWithValue("@PasswordHash", hash);
                cmd.Parameters.AddWithValue("@PasswordSalt", salt);

                using (var reader = await cmd.ExecuteReaderAsync())
                {
                    while (reader.Read())
                    {
                        return Tuple.Create(int.Parse(reader[0].ToString()), reader[1].ToString());
                    }
                    return null;
                }

            }
        }

        private void CreatePasswordHash(string password, out byte[] hash, out byte[] salt)
        {
            using (var hmac = new System.Security.Cryptography.HMACSHA512())
            {
                salt = hmac.Key;
                hash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
        }
    }
}