CREATE TABLE MssqlSyncSecondary.dbo.Users (
                                              Id int IDENTITY(1,1) NOT NULL,
                                              Name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                              Email nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                              CONSTRAINT PK__Users__3214EC07ACEABB87 PRIMARY KEY (Id)
);


CREATE TABLE MssqlSyncSecondary.dbo.MessageOutbox (
                                                      Id int IDENTITY(1,1) NOT NULL,
                                                      EventType nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                                      Payload nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                                      CreatedAt datetime DEFAULT getdate() NULL,
                                                      IsPublished bit DEFAULT 0 NULL,
                                                      CONSTRAINT PK__MessageO__3214EC0710D61F4E PRIMARY KEY (Id)
);


CREATE TRIGGER trg_AfterInsert_Users
    ON Users
    AFTER INSERT
AS
BEGIN
INSERT INTO MessageOutbox (EventType, Payload)
SELECT
    'UserCreated',
    (SELECT * FROM INSERTED FOR JSON AUTO)
END;