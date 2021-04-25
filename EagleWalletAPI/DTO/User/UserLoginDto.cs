using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace EagleWalletAPI.DTO.User
{
    public class UserLoginDto
    {
        [Required]
        [StringLength(16, MinimumLength = 8, ErrorMessage = "You must specify a password between 8 and 16 characters!")]
        public string Password { get; set; }
        [Required]
        public string Email { get; set; }
    }
}
