-- Artist
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Artist` (
  `ArtistId` int,
  `Name` string
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
  'serialization.format' = '1',
  'ignore.malformed.json' = 'TRUE',
  'case.insensitive' = 'FALSE',
  'mapping.artistid' = 'ArtistId',
  'mapping.name' = 'Name'
)
LOCATION 's3://athena-example-bucket/Chinook/Artist'
TBLPROPERTIES ('has_encrypted_data'='false');

-- Album
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Album` (
  `AlbumId` int PRIMARY KEY,
  `ArtistId` int,
  `Title` string
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
  'serialization.format' = '1',
  'ignore.malformed.json' = 'TRUE',
  'case.insensitive' = 'FALSE',
  'mapping.artistid' = 'ArtistId',
  'mapping.albumid' = 'AlbumId',
  'mapping.title' = 'Title'
)
LOCATION 's3://athena-example-bucket/Chinook/Album'
TBLPROPERTIES ('has_encrypted_data'='false');

-- Customer
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Customer` (
  `CustomerId` int,
  `FirstName` string,
  `LastName` string,
  `Company` string,
  `Address` string,
  `City` string,
  `State` string,
  `Country` string,
  `PostalCode` string,
  `Phone` string,
  `Fax` string,
  `Email` string,
  `SupportRepId` int
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
  'serialization.format' = '1',
  'ignore.malformed.json' = 'TRUE',
  'case.insensitive' = 'FALSE',
  'mapping.customerid' = 'CustomerId',
  'mapping.firstname' = 'FirstName',
  'mapping.lastname' = 'LastName',
  'mapping.company' = 'Company',
  'mapping.address' = 'Address',
  'mapping.city' = 'City',
  'mapping.state' = 'State',
  'mapping.country' = 'Country',
  'mapping.postalcode' = 'PostalCode',
  'mapping.phone' = 'Phone',
  'mapping.fax' = 'Fax',
  'mapping.email' = 'Email',
  'mapping.supportrepid' = 'SupportRepId'
)
LOCATION 's3://athena-example-bucket/Chinook/Customer'
TBLPROPERTIES ('has_encrypted_data'='false')

-- Employee
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Employee` (
  `EmployeeId` int,
  `FirstName` string,
  `LastName` string,
  `Title` string,
  `ReportsTo` int,
  `BirtDate` timestamp,
  `HireDate` timestamp,
  `Address` string,
  `City` string,
  `State` string,
  `Country` string,
  `PostalCode` string,
  `Phone` string,
  `Fax` string,
  `Email` string
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
  'serialization.format' = '1',
  'ignore.malformed.json' = 'TRUE',
  'case.insensitive' = 'FALSE',
  'mapping.employeeid' = 'EmployeeId',
  'mapping.firstname' = 'FirstName',
  'mapping.lastname' = 'LastName',
  'mapping.title' = 'Title',
  'mapping.reportsto' = 'ReportsTo',
  'mapping.birthdate' = 'BirthDate',
  'mapping.hiredate' = 'HireDate',
  'mapping.address' = 'Address',
  'mapping.city' = 'City',
  'mapping.state' = 'State',
  'mapping.country' = 'Country',
  'mapping.postalcode' = 'PostalCode',
  'mapping.phone' = 'Phone',
  'mapping.fax' = 'Fax',
  'mapping.email' = 'Email'
)
LOCATION 's3://athena-example-bucket/Chinook/Employee'
TBLPROPERTIES ('has_encrypted_data'='false')

-- Genre
CREATE EXTERNAL TABLE IF NOT EXISTS `Genre` (
  `GenreId` int,
  `Name` string
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.genreid' = 'GenreId',
    'mapping.name' = 'Name'
)
LOCATION 's3://athena-example-bucket/Chinook/Genre'
TBLPROPERTIES ('has_encrypted_data'='false')

-- Invoice
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Invoice` (
  `InvoiceId` int,
  `CustomerId` int,
  `InvoiceDate` timestamp,
  `BillingAddress` string,
  `BillingCity` string,
  `BillingState` string,
  `BillingCountry` string,
  `BillingPostalCode` string,
  `Total` float
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.invoiceid' = 'InvoiceId',
    'mapping.customerid' = 'CustomerId',
    'mapping.invoicedate' = 'InvoiceDate',
    'mapping.billingaddress' = 'BillingAddress',
    'mapping.billingcity' = 'BillingCity',
    'mapping.billingstate' = 'BillingState',
    'mapping.billingcountry' = 'BillingCountry',
    'mapping.billingpostalcode' = 'BillingPostalCode',
    'mapping.total' = 'Total'
)
LOCATION 's3://athena-example-bucket/Chinook/Invoice'
TBLPROPERTIES ('has_encrypted_data'='false')

-- InvoiceLine
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`InvoiceLine` (
  `InvoiceLineId` int,
  `InvoiceId` int,
  `TrackId` int,
  `UnitPrice` float,
  `Quantity` int
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.invoicelineid' = 'InvoiceLineId',
    'mapping.invoiceid' = 'InvoiceId',
    'mapping.trackid' = 'TrackId',
    'mapping.unitprice' = 'UnitPrice',
    'mapping.quantity' = 'Quantity'
)
LOCATION 's3://athena-example-bucket/Chinook/InvoiceLine'
TBLPROPERTIES ('has_encrypted_data'='false')

-- MediaType
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`MediaType` (
  `MediaTypeId` int,
  `Name` string
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.mediatypeid' = 'MediaTypeId',
    'mapping.name' = 'Name'
)
LOCATION 's3://athena-example-bucket/Chinook/MediaType'
TBLPROPERTIES ('has_encrypted_data'='false')

-- Playlist
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Playlist` (
  `PlaylistId` int,
  `Name` string
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.playlistid' = 'PlaylistId',
    'mapping.name' = 'Name'
)
LOCATION 's3://athena-example-bucket/Chinook/Playlist'
TBLPROPERTIES ('has_encrypted_data'='false')

-- PlaylistTrack
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`PlaylistTrack` (
  `PlaylistId` int,
  `TrackId` int
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.playlistid' = 'PlaylistId',
    'mapping.trackid' = 'TrackId'
)
LOCATION 's3://athena-example-bucket/Chinook/PlaylistTrack'
TBLPROPERTIES ('has_encrypted_data'='false')

-- Track
CREATE EXTERNAL TABLE IF NOT EXISTS `chinook`.`Track` (
  `TrackId` int,
  `Name` string,
  `AlbumId` int FOREIGN KEY REFERENCES `chinook`.`Album` (`AlbumId`),
  `MediaTypeId` int FOREIGN KEY REFERENCES `chinook`.`MediaType` (`MediaTypeId`),
  `GenreId` int FOREIGN KEY REFERENCES `chinook`.`Genre` (`GenreId`),
  `Composer` string,
  `Milliseconds` int,
  `Bytes` int,
  `UnitPrice` float,

   PRIMARY KEY (`TrackId`)
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = '1',
    'ignore.malformed.json' = 'TRUE',
    'case.insensitive' = 'FALSE',
    'mapping.trackid' = 'TrackId',
    'mapping.name' = 'Name',
    'mapping.albumid' = 'AlbumId',
    'mapping.mediatypeid' = 'MediaTypeId',
    'mapping.genreid' = 'GenreId',
    'mapping.composer' = 'Composer',
    'mapping.milliseconds' = 'Milliseconds',
    'mapping.bytes' = 'Bytes',
    'mapping.unitprice' = 'UnitPrice'
)
LOCATION 's3://athena-example-bucket/Chinook/Track'
TBLPROPERTIES ('has_encrypted_data'='false')
