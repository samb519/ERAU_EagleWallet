using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.DTO.User
{
    public class UserCreateDto
    {
        [Required]
        public int StudentID { get; set; }
        [Required]
        public string FirstName { get; set; }
        [Required]
        public string LastName { get; set; }
        [Required]
        [StringLength(16, MinimumLength = 8, ErrorMessage = "You must specify a password between 8 and 16 characters!")]
        public string Password { get; set; }
        [Required]
        public string Email { get; set; }
    }
}
