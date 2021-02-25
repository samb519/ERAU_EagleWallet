using System;
using System.Data;
using System.Data.SqlClient;

namespace EagleWalletAPI.Repositories
{
    /// <summary>
    /// Helpful wrapper class for creating and ADO.NET connection.
    /// </summary>
    /// <seealso cref="System.IDisposable" />
    public class DbConnection : IDisposable
    {
        private readonly SqlConnection conn;

        /// <summary>
        /// Initializes a new instance of the <see cref="DbConnection"/> class.
        /// </summary>
        /// <param name="connectionString">The connection string for the database to connect to.</param>
        public DbConnection(string connectionString)
        {
            conn = new SqlConnection(connectionString);
            conn.Open();
        }

        /// <summary>
        /// Creates a SQL command.
        /// </summary>
        /// <param name="commandText">The query to run.</param>
        /// <returns>A SqlCommand to run the supplied query</returns>
        public SqlCommand CreateCommand(string commandText)
        {
            var command = conn.CreateCommand();
            command.CommandText = commandText;
            return command;
        }

        /// <summary>
        /// Creates a stored proc invocation instance.
        /// </summary>
        /// <param name="commandText">The stored proc to invoke.</param>
        /// <returns>A SqlCommand to invoke the supplied stored proc.</returns>
        public SqlCommand CreateStoredProc(string commandText)
        {
            var command = CreateCommand(commandText);
            command.CommandType = CommandType.StoredProcedure;
            return command;
        }

        /// <summary>
        /// Closes the underlying ADO.NET connection.
        /// </summary>
        public void Dispose()
        {
            conn.Close();
            conn.Dispose();
        }
    }
}
