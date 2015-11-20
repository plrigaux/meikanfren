package com.plr;

public enum PageProcessor
{
	TRANSLATION(new PageCleaner()), ALTERNATIVES(new AlternativeCleaner()), NOFOUND(new NotFoundCleaner());
	
	final CleanerInt cleaner;
	
	public CleanerInt getCleaner() {
		return cleaner;
	}

	private PageProcessor(CleanerInt cleaner) {
		this.cleaner = cleaner;
	}
	
}
