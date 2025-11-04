public class BankAccount implements Payment, Transfer
{
    private int sotaikhoan;
    private double tilelaisuat;
    private double sodutaikhoan;

    public BankAccount(int sotaikhoan, double tilelaisuat)
    {
        this.sotaikhoan = sotaikhoan;
        this.tilelaisuat = tilelaisuat;
        this.sodutaikhoan = 50;
    }

    public int getSotaikhoan()
    {
        return this.sotaikhoan;
    }

    public double getTilelaisuat()
    {
        return this.tilelaisuat;
    }

    public void setSotaikhoan(int sotaikhoan)
    {
        this.sotaikhoan = sotaikhoan;
    }

    public void setTilelaisuat(double tilelaisuat)
    {
        this.tilelaisuat = tilelaisuat;
    }

    public void setSodutaikhoan(double sodutaikhoan)
    {
        this.sodutaikhoan = sodutaikhoan;
    }

    @Override
	public boolean pay(double amount)
	{
		double sotiencanthanhtoan = amount;

		if (sotiencanthanhtoan + 50 <= sodutaikhoan)
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
    public void napTien(double amount)
    {
        sodutaikhoan += amount;
    }

    @Override
    public double checkBalance()    // Nap tien
    {
        return this.sodutaikhoan;
    }

    @Override
	public boolean transfer (double amount, Transfer to)
	{
		double sotienchuyen = amount + transferFee * amount;
        double sotiennhan = amount;

        if (sodutaikhoan - sotienchuyen >= 50)
        {
            if (to instanceof BankAccount)
            {
                BankAccount baTo = (BankAccount) to;
                if (sotienchuyen + 50 <= sodutaikhoan)
                {
                    sodutaikhoan = sodutaikhoan - sotienchuyen;
                    baTo.napTien(sotiennhan);
                    return true;
                }
            }
            else if (to instanceof EWallet)
            {
                EWallet ewTo = (EWallet) to;
                if (sotienchuyen + 50 <= sodutaikhoan)
                {
                    sodutaikhoan = sodutaikhoan - sotienchuyen;
                    ewTo.topUp(sotiennhan);
                    return true;
                }
            }
        }
        return false;
	}

    @Override
    public String toString()
    {
        return this.sotaikhoan + "," + this.tilelaisuat + "," + this.sodutaikhoan;
    }
}