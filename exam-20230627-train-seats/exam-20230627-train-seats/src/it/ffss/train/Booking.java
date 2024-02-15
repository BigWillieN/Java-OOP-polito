package it.ffss.train;

public class Booking {
	String id, ssn, name, surname, 
		   begin, end, car, seat, klass;
	boolean checked;

	public Booking(String id, String ssn, String name, String surname, String begin, String end, String car, String seat,  String klass) {
		super();
		this.id = id;
		this.ssn = ssn;
		this.name = name;
		this.surname = surname;
		this.begin = begin;
		this.end = end;
		this.car = car;
		this.seat = seat;
		this.klass = klass;
		this.checked = false;
	}

	public String getId() {
		return id;
	}

	public String getKlass() {
		return klass;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKlass(String klass) {
		this.klass = klass;
	}

	public String getSsn() {
		return ssn;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getBegin() {
		return begin;
	}

	public String getEnd() {
		return end;
	}

	public String getCar() {
		return car;
	}

	public String getSeat() {
		return seat;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}
	public void check() {
		this.checked = true;
	}
	public boolean getChecked() {
		return this.checked;
	}
	
}
