USE EagleWallet;

CREATE TABLE TransactionHistory(
    TransactionId INT IDENTITY(1,1) NOT NULL,
    UserId INT NOT NULL,
    SodexoBucks decimal(12,8) NOT NULL,
    DiningDollars decimal(12,8) NOT NULL,
    EagleDollars decimal(12,8) NOT NULL,
    MealPlans INT NOT NULL,
    TransactionDate DATETiME DEFAULT GETUTCDATE()
);

ALTER TABLE [dbo].[TransactionHistory] ADD  CONSTRAINT [PK_TransactionHistory] PRIMARY KEY CLUSTERED 
(
	[TransactionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
