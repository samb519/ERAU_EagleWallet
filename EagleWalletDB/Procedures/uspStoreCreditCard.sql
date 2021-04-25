USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspStoreCreditCard;
GO

/*

EXEC dbo.uspStoreCreditCard '1234123412341234', 1, '123', '01/00', 'John Smith', '1234 Main Street', 'Every City', 'FL', '12345';

SELECT * FROM dbo.CreditCards;

*/
CREATE PROCEDURE dbo.uspStoreCreditCard
    @CardNumber varchar(16),
    @UserId int,
    @CVC varchar(3),
    @ExpirationDate varchar(5),
    @FullName varchar(64),
    @StreetAddress varchar(64),
    @City varchar(64),
    @State varchar(2),
    @ZipCode varchar(5)
AS
BEGIN

    INSERT INTO dbo.CreditCards(CardNumber, UserId, CVC, ExpirationDate, FullName, StreetAddress, City, State, ZipCode)
    VALUES(@CardNumber, @UserId, @CVC, @ExpirationDate, @FullName, @StreetAddress, @City, @State, @ZipCode);

    SELECT SCOPE_IDENTITY() as CardId;
END
GO
