public class IDCard
{
      private int sodinhdanh;
      private String hoten;
      private String gioitinh;
      private String ngaysinh;
      private String diachi;
      private int sodienthoai;

      public IDCard(int sodinhdanh, String hoten, String gioitinh, String ngaysinh, String diachi, int sodienthoai)
      {
            this.sodinhdanh = sodinhdanh;
            this.hoten = hoten;
            this.gioitinh = gioitinh;
            this.ngaysinh = ngaysinh;
            this.diachi = diachi;
            this.sodienthoai = sodienthoai;
      }

      public int getSodinhdanh()
      {
            return this.sodinhdanh;
      }

      public String getGioitinh()
      {
            return this.gioitinh;
      }

      public String getHoten()
      {
            return this.hoten;
      }

      public String getNgaysinh()
      {
            return this.ngaysinh;
      }

      public String getDiachi()
      {
            return this.diachi;
      }

      public int getSodienthoai()
      {
            return this.sodienthoai;
      }

      public void setSodinhdanh(int sodinhdanh)
      {
            this.sodinhdanh = sodinhdanh;
      }

      public void setHoten(String hoten)
      {
            this.hoten = hoten;
      }

      public void setGioitinh(String gioitinh)
      {
            this.gioitinh = gioitinh;
      }

      public void setNgaysinh(String ngaysinh)
      {
            this.ngaysinh = ngaysinh;
      }

      public void setDiachi(String diachi)
      {
            this.diachi = diachi;
      }

      public void setSodienthoai(int sodienthoai)
      {
            this.sodienthoai = sodienthoai;
      }

      public int Tinhtuoi()
      {
            String[] t = ngaysinh.split("/");
		int namsinh = Integer.parseInt(t[2]);
		int namhientai = 2023;
		return namhientai - namsinh; 
      }

      public String toString()
      {
            return this.sodinhdanh + "," +  this.hoten + "," + this.gioitinh + "," 
                  + this.ngaysinh + "," + this.diachi + "," + this.sodienthoai;
      }
}