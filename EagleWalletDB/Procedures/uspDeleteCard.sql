USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspDeleteCard;
GO

/*

EXEC dbo.uspDeleteCard 1
*/
CREATE PROCEDURE dbo.uspDeleteCard
    @CardId int
AS
BEGIN

    DELETE FROM dbo.CreditCards WHERE CardId = @CardId;

    IF @@ROWCOUNT > 0
    BEGIN
        SELECT @@ROWCOUNT AS NumDeleted;
    END

END
GO
