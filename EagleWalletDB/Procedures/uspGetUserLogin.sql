USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspGetUserLogin;
GO

/*

EXEC dbo.uspGetUserLogin @Email='wiset2@my.erau.edu';

*/
CREATE PROCEDURE dbo.uspGetUserLogin
    @Email nvarchar(max)
AS
BEGIN

    SELECT Id,
            Email,
            PasswordHash,
            PasswordSalt
    FROM dbo.Users U
    WHERE U.Email = @Email AND Validated = 1

END
GO

