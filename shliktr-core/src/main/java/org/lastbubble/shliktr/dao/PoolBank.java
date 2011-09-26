package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IFinalScore;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolAccount;
import org.lastbubble.shliktr.IPoolBank;
import org.lastbubble.shliktr.PoolResult;

import java.util.*;
import static java.util.Map.Entry;

/**
 * @version $Id$
 */
public class PoolBank implements IPoolBank
{
	private Map<IPlayer, PoolAccount> accountsByPlayer =
		new HashMap<IPlayer, PoolAccount>();

	private List<List<PoolAccount>> accountsByWeek;

	private Map<IPlayer, List<PoolResult>> resultsByPlayer =
		new HashMap<IPlayer, List<PoolResult>>();

	private int lastWeek;

	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public PoolBank( List<PoolResult> results )
	{
		List<List<PoolResult>> resultsByWeek = new ArrayList<List<PoolResult>>(17);
		for( int i = 0; i < 17; i++ )
		{
			resultsByWeek.add( new ArrayList<PoolResult>());
		}

		for( PoolResult result : results )
		{
			resultsByWeek.get(result.getWeek() - 1).add(result);

			List<PoolResult> playerResults = resultsByPlayer.get(result.getPlayer());
			if( playerResults == null )
			{
				playerResults = new ArrayList<PoolResult>();
				resultsByPlayer.put(result.getPlayer(), playerResults);
			}
			playerResults.add(result);
		}

		accountsByWeek = new ArrayList<List<PoolAccount>>(17);
		for( int i = 0; i < 17; i++ )
		{
			List<PoolAccount> accounts = computeAccounts(resultsByWeek.get(i));

			accountsByWeek.add(accounts);

			for( PoolAccount account : accounts )
			{
				PoolAccount mainAccount = accountsByPlayer.get(account.getPlayer());
				if( mainAccount != null )
				{
					mainAccount.update(account.getPoints());
				}
				else
				{
					accountsByPlayer.put(account.getPlayer(), account);
				}
			}
		}

		int week = 0;
		for( ; week < resultsByWeek.size(); week++ )
		{
			if( resultsByWeek.get(week).isEmpty() ) break;
		}
		this.lastWeek = Math.max(3, week - 3);
	}


	//---------------------------------------------------------------------------
	// Implements IPoolBank
	//---------------------------------------------------------------------------

	/** @see	IPoolBank#getAccounts */
	public List<? extends IPoolAccount> getAccounts()
	{
		List<PoolAccount> accounts =
			new ArrayList<PoolAccount>(accountsByPlayer.values());

		Collections.sort(accounts);

		return accounts;
	}

	/** @see	IPoolBank#getReserve */
	public int getReserve()
	{
		int total = 0;

		for( List<PoolAccount> accounts : accountsByWeek )
		{
			total += 500 * accounts.size();
		}

		return (int) (.1 * total);
	}

	/** @see	IPoolBank#getFinalScores */
	public List<? extends IFinalScore> getFinalScores()
	{
		List<FinalScore> finalScores = new ArrayList<FinalScore>();

		for( Entry<IPlayer, List<PoolResult>> entry : resultsByPlayer.entrySet() )
		{
			finalScores.add( new FinalScore(entry.getKey(), entry.getValue(), this.lastWeek));
		}

		Collections.sort(finalScores);

		return finalScores;
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	protected List<PoolAccount> computeAccounts( List<PoolResult> results )
	{
		Collections.sort(results);

		int cnt = results.size();
		int total = 500 * cnt;

		List<PoolAccount> accounts = new ArrayList<PoolAccount>(cnt);

		int firstPlaceCnt = countFirstPlaceWinners(results);

		if( firstPlaceCnt > 0 )
		{
			int loserPos = 2;

			if( firstPlaceCnt > 1 )
			{
				int winnings = (int) ((.9 * total) / firstPlaceCnt);

				for( int i = 0; i < firstPlaceCnt; i++ )
				{
					accounts.add( new PoolAccount(
							results.get(i).getPlayer(), winnings - 500
						)
					);
				}

				loserPos = firstPlaceCnt;
			}
			else
			{
				accounts.add( new PoolAccount(
						results.get(0).getPlayer(), (int) (.7 * total) - 500
					)
				);

				if( cnt > 1 )
				{
					accounts.add( new PoolAccount(
							results.get(1).getPlayer(), (int) (.2 * total) - 500
						)
					);
				}
			}

			for( ; loserPos < cnt; loserPos++ )
			{
				accounts.add( new PoolAccount(results.get(loserPos).getPlayer(), -500));
			}
		}

		return accounts;
	}

	protected int countFirstPlaceWinners( List<PoolResult> results )
	{
		int cnt = results.size();

		if( cnt <= 1 ) { return cnt; }

		PoolResult firstResult = results.get(0);

		int winnerCnt = 1;

		for( int i = 1; i < cnt; i++ )
		{
			PoolResult result = results.get(i);

			if( result.getPoints() < firstResult.getPoints() ||
				result.getTiebreakerDiff() > firstResult.getTiebreakerDiff() )
			{
				break;
			}

			winnerCnt++;
		}

		return winnerCnt;
	}
}