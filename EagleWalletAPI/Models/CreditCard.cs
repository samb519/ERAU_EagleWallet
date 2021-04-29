using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace EagleWalletAPI.Models
{
    public class CreditCard
    {
        public int CardId { get; set; }

        public int UserId {get; set;}
        
        [Required]
        [StringLength(16, MinimumLength=16, ErrorMessage = "The card number must be exactly 16 digits.")]
        public string CardNumber { get; set; }

        [Required]
        [StringLength(3, MinimumLength=3, ErrorMessage = "The CVC must be exactly 3 digits.")]
        public string CVC { get; set; }
        [Required]
        [StringLength(5,MinimumLength=5, ErrorMessage = "The Expiration Date must be in the format MM/YY.")]
        public string ExpirationDate { get; set; }

        [Required]
        [StringLength(64, ErrorMessage = "The Name must be less than 64 characters.")]
        public string FullName { get; set; }

        [Required]
        [StringLength(64, ErrorMessage = "The Street Address must be less than 64 characters.")]
        public string StreetAddress { get; set; }

        [Required]
        [StringLength(64, ErrorMessage = "The City must be less than 64 characters.")]
        public string City { get; set; }

        [Required]
        [StringLength(2, MinimumLength=2, ErrorMessage = "The State must be exactly 2 characters.")]
        public string State { get; set; }

        [Required]
        [StringLength(5, MinimumLength=5, ErrorMessage = "The Zip Code must be exactly 5 characters.")]
        public string ZipCode { get; set; }

    }
}
