using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.DTO.User
{
    public class UserCreateDto : UserLoginDto
    {
        [Required]
        [Range(1000000, 9999999, ErrorMessage = "You must input an ID 7 digits long")]
        public int StudentID { get; set; }
        [Required]
        public string FirstName { get; set; }
        [Required]
        public string LastName { get; set; }
    }
}
