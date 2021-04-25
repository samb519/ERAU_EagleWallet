using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Models
{
    public class TransactionHistory : UserBalances
    {
        public int TransactionId { get; set; }
        public DateTime TransactionDate { get; set; }
    }
}