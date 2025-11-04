public class EWallet implements Payment, Transfer
{
	private int sodienthoai;
	private double sodutaikhoan;

	public EWallet(int sodienthoai)
	{
		this.sodienthoai = sodienthoai;
		this.sodutaikhoan = 0;
	}

	public int getSodienthoai()
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(int sodienthoai)
	{
		this.sodienthoai = sodienthoai;
	}

	public void setSodutaikhoan(double sodutaikhoan)
	{
		this.sodutaikhoan = sodutaikhoan;
	}

	@Override
	public boolean pay(double amount)
	{
		double sotiencanthanhtoan = amount;

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

	// Phương thức nạp tiền
	public void topUp(double amount)
	{
        sodutaikhoan += amount;
    }

	@Override
	public double checkBalance()    // Nap tien
	{
		return this.sodutaikhoan;
	}

	@Override
	public boolean transfer(double amount, Transfer to)
	{
		double sotienchuyen = amount + transferFee * amount;
		double sotiennhan = amount;

		if (to instanceof EWallet)
		{
            EWallet ewTo = (EWallet) to;
            if (sotienchuyen <= sodutaikhoan)
			{
                sodutaikhoan = sodutaikhoan - sotienchuyen;
                ewTo.topUp(sotiennhan);
                return true;
            }
        }
		else if (to instanceof BankAccount)
		{
			BankAccount baTo = (BankAccount) to;
			if (sotienchuyen <= sodutaikhoan)
			{
				sodutaikhoan = sodutaikhoan - sotienchuyen;
				baTo.napTien(sotiennhan);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return this.sodienthoai + "," + this.sodutaikhoan;
	}
}