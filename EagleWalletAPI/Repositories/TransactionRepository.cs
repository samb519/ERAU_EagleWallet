using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Repositories
{
    public class TransactionRepository: ITransactionRepository
    {
        private IDbConnectionProvider context;
        
        public TransactionRepository(IDbConnectionProvider context){

            this.context = context ?? throw new ArgumentNullException(nameof(context));
        }
        
        public async Task<UserBalances> GetUserBalances(int userId){
            UserBalances result = null;
            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspGetUserBalances"))
            {
                cmd.Parameters.AddWithValue("@UserId", userId);
                using (var reader = await cmd.ExecuteReaderAsync())
                {
                    while (reader.Read())
                    {
                        result = new UserBalances(){
                            UserId = int.Parse(reader["UserId"].ToString()),
                            SodexoBucks = double.Parse(reader["SodexoBucks"].ToString()),
                            DiningDollars = double.Parse(reader["DiningDollars"].ToString()),
                            EagleDollars = double.Parse(reader["EagleDollars"].ToString()),
                            MealPlans = int.Parse(reader["MealPlanCredits"].ToString()),
                        };
                    }
                }
            }
            return result;
        }

        public async Task<UserBalances> ModifyBalances(UserBalances balances){
            UserBalances result = null;
            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspModifyUserBalances"))
            {
                cmd.Parameters.AddWithValue("@UserId", balances.UserId);
                cmd.Parameters.AddWithValue("@SodexoBucks", balances.SodexoBucks);
                cmd.Parameters.AddWithValue("@DiningDollars", balances.DiningDollars);
                cmd.Parameters.AddWithValue("@EagleDollars", balances.EagleDollars);
                cmd.Parameters.AddWithValue("@MealPlans", balances.MealPlans);
                using (var reader = await cmd.ExecuteReaderAsync())
                {
                    while (reader.Read())
                    {
                        result = new UserBalances(){
                            UserId = int.Parse(reader["UserId"].ToString()),
                            SodexoBucks = double.Parse(reader["SodexoBucks"].ToString()),
                            DiningDollars = double.Parse(reader["DiningDollars"].ToString()),
                            EagleDollars = double.Parse(reader["EagleDollars"].ToString()),
                            MealPlans = int.Parse(reader["MealPlanCredits"].ToString()),
                        };
                    }
                }
            }
            return result;
        }
    }
}
