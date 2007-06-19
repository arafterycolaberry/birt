
package org.eclipse.birt.report.engine.executor;

import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IListGroupContent;
import org.eclipse.birt.report.engine.ir.ListGroupDesign;

public class ListGroupExecutor extends GroupExecutor
{

	protected ListGroupExecutor( ExecutorManager manager )
	{
		super( manager );
	}

	public void close( )
	{
		
		handlePageBreakAfterExclusingLast( );
		handlePageBreakAfter();
		finishGroupTOCEntry( );
		super.close( );
		manager.releaseExecutor( ExecutorManager.LISTGROUPITEM, this );
	}

	public IContent execute( )
	{
		ListGroupDesign groupDesign = (ListGroupDesign) getDesign( );

		IListGroupContent groupContent = report.createListGroupContent( );
		setContent( groupContent );

		restoreResultSet( );
		
		initializeContent( groupDesign, groupContent );
		processSoftPageBreakBefore( );
		processBookmark( groupDesign, groupContent );
		handlePageBreakBeforeOfGroup( );
		handlePageBreakAfterOfGroup( );		
		handlePageBreakAfterOfPreviousGroup( );
		handlePageBreakBefore();
		startGroupTOCEntry( groupContent );

		// prepare to execute the children
		prepareToExecuteChildren( );

		return groupContent;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.birt.report.engine.executor.GroupExecutor#getNextChild()
	 */
	public IReportItemExecutor getNextChild( )
	{
		IReportItemExecutor executor = super.getNextChild( );
		if ( executor instanceof ListBandExecutor )
		{
			ListBandExecutor bandExecutor = (ListBandExecutor) executor;
			bandExecutor.setListingExecutor(  listingExecutor );
		}
		return executor;
	}
}