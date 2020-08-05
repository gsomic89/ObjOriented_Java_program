package shop.data;

/**
 * Implementation of Video interface.
 * @see Data
 */
final class VideoObj implements Video {
  private final String _title;
  private final int    _year;
  private final String _director;

  /**
   * Initialize all object attributes.
   * Title and director are "trimmed" to remove leading and final space.
   * @throws IllegalArgumentException if object invariant violated.
   */
  public VideoObj(String title, int year, String director) {
		if(title == null) {
			throw new IllegalArgumentException("Invalid title");
		}
		if(director == null) {
			throw new IllegalArgumentException("Invalid director");
		}
		title = title.trim();
		director = director.trim();
		
		if(title.length() == 0 || title.startsWith(" ") || title.endsWith(" ")) {
			throw new IllegalArgumentException("Invalid title");
		}
		if(director.length() == 0 || director.startsWith(" ") || director.endsWith(" ")) {
			throw new IllegalArgumentException("Invalid director");
		}
		if(year <= 1800 || year >=5000) {
			throw new IllegalArgumentException("Invalid year");
		}
		_title = title;
		_director = director;
		_year = year;
  }

  public String director() {
    // TODO  
    return _director;
  }

  public String title() {
    // TODO  
    return _title;
  }

  public int year() {
    // TODO  
    return _year;
  }

  public boolean equals(Object thatObject) {
    // TODO  
		if(thatObject instanceof VideoObj) {
			VideoObj next = (VideoObj) thatObject;
			return (this._director.contentEquals(next.director()) && this._title.contentEquals(next.title()) && this._year == next.year());
		}
	    return false;
	  }


  public int hashCode() {
    // TODO  
		int res = 17;
		int prime = 37;
		res = prime * res + _title.hashCode();
		res = prime * res + _year;
		res = prime * res + _director.hashCode();
	    return res;
  }

  public int compareTo(Object thatObject) {
		VideoObj next = (VideoObj) thatObject;
		if(this._title.compareTo(next._title)!=0 ) {
			return this._title.compareTo(next._title);
		}
		else {
			if(this._year != next._year) {
				return this._year - next._year;
			}
			else {
				return this._director.compareTo(next._director);
			}
		}
  }

  public String toString() {
    // TODO  
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(_title);
	    buffer.append(" (");
	    buffer.append(_year);
	    buffer.append(") ");
	    buffer.append(": ");
	    buffer.append(_director);
	    return buffer.toString();
  }
}
