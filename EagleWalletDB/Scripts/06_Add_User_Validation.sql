USE EagleWallet;
GO;

ALTER TABLE Users
ADD Validated bit DEFAULT CAST(0 AS BIT);