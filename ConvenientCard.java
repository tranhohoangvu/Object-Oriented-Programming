public class ConvenientCard implements Payment
{
    private String type;	
	private IDCard thedinhdanh;
	private double sodutaikhoan;

	public ConvenientCard(IDCard thedinhdanh) throws CannotCreateCard
	{
		this.type = "Student";
		this.thedinhdanh = thedinhdanh;
		this.sodutaikhoan = 100;
		tinhTuoi();
	}

	public void tinhTuoi() throws CannotCreateCard
	{
		String ns = thedinhdanh.getNgaysinh();
		String[] t = ns.split("/");
		int namsinh = Integer.parseInt(t[2]);
		int namhientai = 2023;
		int tuoi = namhientai - namsinh;
		
		if (tuoi < 12)
		{
			throw new CannotCreateCard("Not enough age");
		}
		else if (tuoi <= 18)
		{
			this.type = "Student";
		}
		else
		{
			this.type = "Adult";
		}
	}

	public String getType()
	{
		return this.type;
	}

	public IDCard getThedinhdanh()
	{
		return this.thedinhdanh;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setThedinhdanh(IDCard thedinhdanh)
	{
		this.thedinhdanh = thedinhdanh;
	}

	public void setSodutaikhoan(double sodutaikhoan)
	{
		this.sodutaikhoan = sodutaikhoan;
	}

	public void topUp(double amount)
	{
		this.sodutaikhoan += amount;
	}

	@Override
	public boolean pay(double amount)
	{
		double sotiencanthanhtoan = amount;

		if (type.equals("Student"))
		{
			sotiencanthanhtoan = amount;
		}
		if (type.equals("Adult"))
		{
			sotiencanthanhtoan = amount + 0.01 * amount;
		}

		if (sotiencanthanhtoan <= sodutaikhoan)
		{
			sodutaikhoan = sodutaikhoan - sotiencanthanhtoan;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public double checkBalance()    // Nap tien
	{
		return this.sodutaikhoan;
	}

	@Override
	public String toString()
	{
		return this.thedinhdanh + "," + this.type + "," + this.sodutaikhoan;
	}
}