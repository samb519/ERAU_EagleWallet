using EagleWalletAPI.DTO.User;
using EagleWalletAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Repositories
{
    public class CardRepository : ICardRepository
    {
        private IDbConnectionProvider context;
        
        public CardRepository(IDbConnectionProvider context){

            this.context = context ?? throw new ArgumentNullException(nameof(context));
        }
        
        public async Task<CreditCard> AddUserCard(CreditCard card){
            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspStoreCreditCard"))
            {
                cmd.Parameters.AddWithValue("@UserId", card.UserId);
                cmd.Parameters.AddWithValue("@CardNumber", card.CardNumber);
                cmd.Parameters.AddWithValue("@CVC", card.CVC);
                cmd.Parameters.AddWithValue("@ExpirationDate", card.ExpirationDate);
                cmd.Parameters.AddWithValue("@FullName", card.FullName);
                cmd.Parameters.AddWithValue("@StreetAddress", card.StreetAddress);
                cmd.Parameters.AddWithValue("@City", card.City);
                cmd.Parameters.AddWithValue("@State", card.State);
                cmd.Parameters.AddWithValue("@ZipCode", card.ZipCode);
                using (var reader = await cmd.ExecuteReaderAsync())
                {
                    while (reader.Read())
                    {
                        card.CardId = int.Parse(reader["CardId"].ToString());
                        return card;
                    }

                }
            }
            return null;
        }

        public async Task<bool> DeleteCard(int cardId){
            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspDeleteCard"))
            {
                cmd.Parameters.AddWithValue("@CardId", cardId);
                using (var reader = await cmd.ExecuteReaderAsync())
                    while (reader.Read())
                        return true;

            }
            return false;
        }
        
        public async Task<List<CreditCard>> GetUserCards(int userId){
            using (var conn = context.GetEagleWalletConnection())
            using (var cmd = conn.CreateStoredProc("uspGetUserCards"))
            {
                cmd.Parameters.AddWithValue("@UserId", userId);
                var result = new List<CreditCard>();
                using (var reader = await cmd.ExecuteReaderAsync())
                {
                    while (reader.Read())
                    {
                        var card = new CreditCard();
                        card.CardId = int.Parse(reader["CardId"].ToString());
                        card.UserId = int.Parse(reader["UserId"].ToString());
                        card.CardNumber = reader["CardNumber"].ToString();
                        card.CVC = reader["CVC"].ToString();
                        card.ExpirationDate = reader["ExpirationDate"].ToString();
                        card.FullName = reader["FullName"].ToString();
                        card.StreetAddress = reader["StreetAddress"].ToString();
                        card.City = reader["City"].ToString();
                        card.State = reader["State"].ToString();
                        card.ZipCode = reader["ZipCode"].ToString();
                        result.Add(card);
                    }

                }
                return result;
            }
        }
    }
}
