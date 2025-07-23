CREATE TABLE MssqlSyncPrimary.dbo.Users (
                                            Id int IDENTITY(1,1) NOT NULL,
                                            Name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                            Email nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                            CONSTRAINT PK__Users__3214EC07ACEABB87 PRIMARY KEY (Id)
);