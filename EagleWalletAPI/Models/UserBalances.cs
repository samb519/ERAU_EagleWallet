using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.Models
{
    public class UserBalances
    {
        public int UserId { get; set; }
        public double SodexoBucks { get; set; }
        public double DiningDollars { get; set; }
        public double EagleDollars { get; set; }
        public int MealPlans { get; set; }
    }
}
