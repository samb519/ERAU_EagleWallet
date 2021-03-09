USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspGetUserBalances;
GO

/*

EXEC dbo.uspGetUserBalances @UserId=20

*/
CREATE PROCEDURE dbo.uspGetUserBalances
    @UserId INT
AS
BEGIN

    SELECT AB.UserId,
            AB.SodexoBucks,
            AB.DiningDollars,
            AB.EagleDollars,
            ISNULL(MP.CurrentBalance, -1) AS MealPlanCredits
    FROM dbo.AccountBalances AB
    LEFT JOIN dbo.MealPlans MP ON MP.UserId = AB.UserId AND ExpirationDate > GETDATE()
    WHERE AB.UserId = @UserId;

END
GO
