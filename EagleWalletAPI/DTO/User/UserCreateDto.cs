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
        [StringLength(16, MinimumLength = 8, ErrorMessage = "You must specify a username between 8 & 16 characters!")]
        public string Username { get; set; }
        [Required]
        [StringLength(16, MinimumLength = 8, ErrorMessage = "You must specify a password between 8 and 16 characters!")]
        public string Password { get; set; }
        [Required]
        public string Email { get; set; }
    }
}
