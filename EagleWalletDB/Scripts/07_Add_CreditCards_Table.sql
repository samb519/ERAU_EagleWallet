
USE EagleWallet;

CREATE TABLE [dbo].[CreditCards](
	[CardId] [int] IDENTITY(1, 1) NOT NULL,
    [UserId] [int] NOT NULL,
	[CardNumber] [varchar](16) NOT NULL,
	[CVC] [varchar](3) NOT NULL,
    [ExpirationDate] varchar(5) NOT NULL,
    [FullName] varchar(64) NOT NULL,
    [StreetAddress] varchar(64) NOT NULL, 
    [City] varchar(64) NOT NULL,
    [State] varchar(2) NOT NULL,
    [ZipCode] varchar(5) NOT NULL 
)
GO
ALTER TABLE [dbo].[CreditCards] ADD  CONSTRAINT [PK_CreditCards] PRIMARY KEY CLUSTERED 
(
	[CardId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO