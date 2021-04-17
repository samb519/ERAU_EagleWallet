USE EagleWallet;
GO

DROP PROCEDURE IF EXISTS dbo.uspCreateUser;
GO

/*

declare @hexstring varchar(max);
set @hexstring = '0xabcedf012439';
declare @hexbinary varbinary(max);
SET @hexbinary = convert(varbinary(max), @hexstring, 1);

EXEC dbo.uspCreateUser @Username='hello 2', @Email='world', @PasswordHash=@hexbinary,  @PasswordSalt=@hexbinary

*/
CREATE PROCEDURE dbo.uspCreateUser
    @Username nvarchar(max),
    @Email nvarchar(max),
    @PasswordHash varbinary(max),
    @PasswordSalt varbinary(max)
AS
BEGIN

    IF EXISTS(SELECT * FROM Users U WHERE U.Username = @Username) 
    BEGIN
        SELECT -1 as UserId,
                'Username already exists!' as Message
        RETURN
    END

    IF EXISTS(SELECT * FROM Users U WHERE U.Email = @Email) 
    BEGIN
        SELECT -1 as UserId,
                'User already exists with that email!' as Message
        RETURN
    END

    INSERT INTO dbo.Users (Username, Email, PasswordHash, PasswordSalt)
    VALUES (@Username, @Email, @PasswordHash, @PasswordSalt);

    SELECT SCOPE_IDENTITY() as UserId,
            'User successfully created!' as Message;

    INSERT INTO AccountBalances (UserId) VALUES (SCOPE_IDENTITY()); 

END
GO
