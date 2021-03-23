USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspGetUserLogin;
GO

/*

EXEC dbo.uspGetUserLogin @Username='hello'

*/
CREATE PROCEDURE dbo.uspGetUserLogin
    @Username nvarchar(max)
AS
BEGIN

    SELECT Id,
            Username,
            Email,
            PasswordHash,
            PasswordSalt
    FROM dbo.Users U
    WHERE U.Username = @Username

END
GO
