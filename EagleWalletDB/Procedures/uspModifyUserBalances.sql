USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspModifyUserBalances;
GO

/*

EXEC dbo.uspModifyUserBalances @UserId=45, @SodexoBucks=20.0, @DiningDollars=20.0, @EagleDollars=20.0, @MealPlans=0

SELECT * FROM TransactionHistory

*/
CREATE PROCEDURE dbo.uspModifyUserBalances
    @UserId INT,
    @SodexoBucks decimal(12,8),
    @DiningDollars decimal(12,8),
    @EagleDollars decimal(12,8),
    @MealPlans INT
AS
BEGIN

    DECLARE @FoundUser INT = -1
    DECLARE @NewSodexo DECIMAL(12, 8) = -1.0;
    DECLARE @NewDining DECIMAL(12, 8) = -1;
    DECLARE @NewEagle DECIMAL(12, 8) = -1;
    DECLARE @NewMeals INT = 0;

    SELECT 
            @FoundUser = AB.UserId,
            @NewSodexo = AB.SodexoBucks + @SodexoBucks,
            @NewDining = AB.DiningDollars + @DiningDollars,
            @NewEagle = AB.EagleDollars + @EagleDollars,
            @NewMeals = ISNULL(MP.CurrentBalance, 0) + @MealPlans
    FROM dbo.AccountBalances AB
    LEFT JOIN dbo.MealPlans MP ON MP.UserId = AB.UserId AND ExpirationDate > GETDATE()
    WHERE AB.UserId = @UserId;

    IF (@FoundUser < 0)
    BEGIN;
        THROW 99001, 'User not found.', 1;
    END
    ELSE IF (@NewSodexo < 0 OR @NewDining < 0 OR @NewEagle < 0 OR @NewMeals < 0)
    BEGIN;
        THROW 99001, 'Negative funds would be created as a result of this operation.', 1;
    END
    ELSE
    BEGIN
        UPDATE AB
        SET
            AB.DiningDollars = @NewDining,
            AB.SodexoBucks = @NewSodexo,
            AB.EagleDollars = @NewEagle
        FROM AccountBalances AB
        WHERE AB.UserId = @UserId

        UPDATE MP
        SET
            MP.CurrentBalance = @NewMeals 
        FROM MealPlans MP
        WHERE MP.ExpirationDate > GETDATE() AND MP.UserId = @UserId
        
        INSERT INTO TransactionHistory(UserId, SodexoBucks, DiningDollars, EagleDollars, MealPlans)
        VALUES (@UserId, @SodexoBucks, @DiningDollars, @EagleDollars, @MealPlans);

        EXEC dbo.uspGetUserBalances @UserId=@UserId

    END
    
END
GO
