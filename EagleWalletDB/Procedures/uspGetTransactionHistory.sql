USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspGetTransactionHistory;
GO

/*

EXEC dbo.uspGetTransactionHistory @UserId=45

*/
CREATE PROCEDURE dbo.uspGetTransactionHistory
    @UserId INT
AS
BEGIN

    SELECT TransactionId,
            UserId,
            SodexoBucks,
            DiningDollars,
            EagleDollars,
            MealPlans,
            TransactionDate
    FROM TransactionHistory
    WHERE UserId = @UserId
    ORDER BY TransactionDate
    
END
GO
