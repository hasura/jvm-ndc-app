The `chinook-ansi-multirow-inserts.sql` is for Snowflake (but could be used elsewhere probably)

Snowflake's GUI and JDBC driver only let you run a single statement at a time, so you can't have 10,000's of INSERT
statements separated by semicolons.

I refactored one of the other DB seed files into this to be able to seed Snowflake with test data easily.
