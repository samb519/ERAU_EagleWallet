namespace OneSkyOptimizer.Optimization.Repositories.Core
{
    /// <summary>
    /// A helper interface defined for how to get a <see cref="DbConnection"/> to the Flexjet and Sentient DBs.
    /// </summary>
    public interface IDbConnectionProvider
    {
        /// <summary>
        /// Gets an open connection to the Flexjet database.
        /// </summary>
        /// <returns>An open connection to the Flexjet database.</returns>
        DbConnection GetEagleWalletConnection();

    }
}
