USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspGetUserCards;
GO

/*

EXEC dbo.uspGetUserCards 1;

*/
CREATE PROCEDURE dbo.uspGetUserCards
    @UserId int
AS
BEGIN

    SELECT CardId, 
            CardNumber, 
            UserId, 
            CVC, 
            ExpirationDate, 
            FullName, 
            StreetAddress, 
            City, 
            State, 
            ZipCode
    FROM dbo.CreditCards
    WHERE UserId = @UserId;
    
END
GO
