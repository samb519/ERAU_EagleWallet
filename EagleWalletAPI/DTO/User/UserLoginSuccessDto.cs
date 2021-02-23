using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EagleWalletAPI.DTO.User
{
    public class UserLoginSuccessDto : UserDetailedDto
    {
        public string LoginToken { get; set; }
    }
}
