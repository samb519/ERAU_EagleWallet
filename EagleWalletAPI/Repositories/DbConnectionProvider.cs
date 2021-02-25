using Microsoft.Extensions.Configuration;
using System;

namespace EagleWalletAPI.Repositories
{
    /// <summary>
    /// The implementation of a connection provider toi read connection information from the environment variables.
    /// </summary>
    /// <seealso cref="IDbConnectionProvider" />
    public class DbConnectionProvider : IDbConnectionProvider
    {

        private readonly IConfiguration configuration;

        public DbConnectionProvider(IConfiguration configuration)
        {
            this.configuration = configuration ?? throw new ArgumentNullException(nameof(configuration));
        }

        /// <summary>
        /// Gets an open connection to the EagleWallet database.
        /// </summary>
        /// <returns>
        /// An open connection to the EagleWallet database.
        /// </returns>
        public DbConnection GetEagleWalletConnection()
        {
            return new DbConnection(configuration.GetValue<string>("EagleWalletConnectionString"));
        }

    }
}
