USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspValidateUser;
GO

/*

EXEC dbo.uspValidateUser 34;

*/
CREATE PROCEDURE dbo.uspValidateUser
    @UserID int
AS
BEGIN

    UPDATE Users 
    SET Validated = 1 
    WHERE Id = @UserID;


    SELECT Id
    FROM dbo.Users U
    WHERE U.Id = @UserID AND Validated = 1

END
GO
