/**************************************************************************************
*		              ����ͨ��ʵ��												  *
ʵ���������س����򿪴��ڵ������֣�������������Ϊ4800��ѡ���͵����ݾͿ�����ʾ
			�ڴ��������ϡ�
ע������ޡ�																				  
***************************************************************************************/

#include "reg52.h"			 //���ļ��ж����˵�Ƭ����һЩ���⹦�ܼĴ���
#include <intrins.h>

sbit LED1_Green=P0^0;
sbit LED1_Blue=P0^1;
sbit LED1_Red=P0^2;

sbit LED2_Mode=P1^0;
sbit LED2_Speed=P1^1;
sbit LED2_Color=P1^2;

sfr ISP_DATA = 0xe2;   
sfr ISP_ADDRH = 0xe3;     
sfr ISP_ADDRL = 0xe4;   
sfr ISP_CMD = 0xe5;   
sfr ISP_TRIG = 0xe6;      
sfr ISP_CONTR = 0xe7;

typedef unsigned int u16;	  
typedef unsigned char u8;

int LED1_flash=0;

int LED2_colour=0;
int LED2_lightness=0;
int LED2_speed=0;
int LED2_mode=0;
int LED2_flag=1;
int LED2_num=4;
int LED2=1;


u8 receiveData;
u8 u1_buff[4];
u8 JS_FLAG = 0;

void  cc(u16 addr);
void  xcx(u16 addr,u8 dat);
void  xcx_f(u16 addr,u8 dat);
u8 dcx(u16 addr);
void  Q0();
void delay_1s(int i);
void delay(u16 i);
void UsartInit();

/*******************************************************************************
* �� �� ��       : main
* ��������		 : ������
* ��    ��       : ��
* ��    ��    	 : ��
*******************************************************************************/
void main()
{	
	UsartInit();  //	���ڳ�ʼ��
	
	LED2_colour=dcx(0x2002);
	LED2_lightness=dcx(0x2003);
	LED2_speed=dcx(0x2004);
	LED2_mode=dcx(0x2005);
	LED2_flag=dcx(0x2006);
	
	while(1){
			if(u1_buff[0]==0xab && u1_buff[3]==0x55){
				if(u1_buff[1]==0x00){
					switch(u1_buff[2]){
						case 0x00:
							cc(0x2000);
							xcx(0x2002,1);
							xcx(0x2003,1);
							xcx(0x2004,1);
							xcx(0x2005,0x21);
							xcx(0x2006,1);
							break;
						case 0x01:
							LED1_Green=0;
							LED1_Blue=0;
							LED1_Red=1;
							break;
						case 0x02:
							LED1_Green=1; 
							LED1_Blue=0;
							LED1_Red=0;
							break;
						case 0x03:
							LED1_Green=0;
							LED1_Blue=1;
							LED1_Red=0;
							break;
						case 0x04:
							LED1_Green=1;
							LED1_Blue=0;
							LED1_Red=1;
							break;
						case 0x05:
							LED1_Green=0;
							LED1_Blue=1;
							LED1_Red=1;
							break;
						case 0x06:
							LED1_Green=1;
							LED1_Blue=1;
							LED1_Red=0;
							break;
						case 0x07:
							LED1_Green=1;
							LED1_Blue=1;
							LED1_Red=1;
							break;
						case 0x08:
							LED1_flash=1;
							break;
						case 0x09:
							LED1_Green=0; 
							LED1_Blue=0;
							LED1_Red=0;   
							break;
					}
					if(u1_buff[2]!=0x08){
						LED1_flash=0;
					}
				}
				
				if(u1_buff[1]==0x01){
					if(LED2==0&&u1_buff[2]!=0x18){
						if(u1_buff[2]<0x15||u1_buff[2]==0x17){
							LED2_Color=0;
							delay(150);
							LED2_Color=1;
							delay(150);
							LED2=1;
						}else if(u1_buff[2]<0x30||u1_buff[2]==0x15){
							LED2_Mode=0;
							delay(150);
							LED2_Mode=1;
							delay(150);
							LED2=1;
						}
					}else if(u1_buff[2]<0x15){
						if(LED2_flag==2){
							LED2_Color=0;
							delay(150);
							LED2_Color=1;
							delay(150);
							LED2_flag=1;
						}
						while(LED2_colour!=u1_buff[2]){
							LED2_colour = LED2_colour%20;
							LED2_Color=0;
							delay(150);
							LED2_Color=1;
							delay(150);
							LED2_colour++;
						}
						xcx_f(0x2002,LED2_colour);
					}else if(u1_buff[2]<0x20)
						switch(u1_buff[2]){
							case 0x15:
								LED2_Mode=0;
								delay(200);
								LED2_Mode=1;
								if(LED2_flag==1){
									LED2_flag=2;
								}else{
									LED2_mode++;
									xcx_f(0x2003,LED2_mode);
								}
								break;
							case 0x16:
//								LED2_Speed=0;
//								delay(200);
//								LED2_Speed=1;
//								LED2_colour++;
								break;
							case 0x17:
								LED2_Color=0;
								delay(150);
								LED2_Color=1;
								delay(150);
								if(LED2_flag==2){
									LED2_flag=1;
								}else{
									LED2_colour++;
									xcx_f(0x2002,LED2_colour);
								}
								break;
							case 0x18:
								LED2_Speed=0;
								delay_1s(2);
								LED2_Speed=1;
							  LED2=0;
								break;
					}else if(u1_buff[2]<0x30){
						if(LED2_flag==1){
							LED2_Mode=0;
							delay(150);
							LED2_Mode=1;
							delay(150);
							LED2_flag=2;
						}
						while(LED2_mode!=u1_buff[2]){
							if(LED2_mode==0x2d)
								LED2_mode=0x20;
							LED2_Mode=0;
							delay(150);
							LED2_Mode=1;
							delay(150);
							LED2_mode++;
						}
						xcx_f(0x2005,LED2_mode);
				 }else if(u1_buff[2]<0x50&&LED2==1){
					 switch(u1_buff[2]){
							case 0x41:
								if(LED2_flag==1&&LED2_lightness!=5){
									LED2_num=4;
									while(LED2_num--!=0){
										LED2_Speed=0;
										delay(150);
										LED2_Speed=1;
										delay(150);
									}
									LED2_lightness++;
									xcx_f(0x2003,LED2_lightness);
								}
								break;
							case 0x42:
								if(LED2_flag==1&&LED2_lightness!=1){
									if(LED2_lightness==0)
										LED2_lightness=5;
									LED2_Speed=0;
									delay(150);
									LED2_Speed=1;
									LED2_lightness--;
									xcx_f(0x2003,LED2_lightness);
								}
								break;
							case 0x43:
								if(LED2_flag==2&&LED2_speed!=5){
									LED2_num=4;
									while(LED2_num--!=0){
										LED2_Speed=0;
										delay(150);
										LED2_Speed=1;
										delay(150);
									}
									LED2_speed++;
									xcx_f(0x2004,LED2_speed);
								}
								break;
							case 0x44:
								if(LED2_flag==2&&LED2_speed!=1){
									LED2_Speed=0;
									delay(150);
									LED2_Speed=1;
									LED2_speed--;
									xcx_f(0x2004,LED2_speed);
								}
								break;
					}
				}
			}
			u1_buff[3]=0;
		}
			
		if(LED1_flash>0){
			switch(LED1_flash){
				case 0x01:
					LED1_Green=0;
					LED1_Blue=0;
					LED1_Red=1;
					break;
				case 0x02:
					LED1_Green=1; 
					LED1_Blue=0;
					LED1_Red=0;
					break;
				case 0x03:
					LED1_Green=0;
					LED1_Blue=1;
					LED1_Red=0;
					break;
				case 0x04:
					LED1_Green=1;
					LED1_Blue=0;
					LED1_Red=1;
					break;
				case 0x05:
					LED1_Green=0;
					LED1_Blue=1;
					LED1_Red=1;
					break;
				case 0x06:
					LED1_Green=1;
					LED1_Blue=1;
					LED1_Red=0;
					break;
				case 0x07:
					LED1_Green=1;
					LED1_Blue=1;
					LED1_Red=1;
					LED1_flash=1;
					break;
			}
			delay_1s(1);
			LED1_flash++;
		}
		
	}		
}

/*******************************************************************************
* ������         : Usart() interrupt 4
* ��������		  : ����ͨ���жϺ���
* ����           : ��
* ���         	 : ��
*******************************************************************************/
void Usart() interrupt 4
{
	receiveData=SBUF;//��ȥ���յ�������
	RI = 0;//��������жϱ�־λ
			
	if(receiveData == 0xab && JS_FLAG == 0)
		JS_FLAG = 1;
	
	if(JS_FLAG > 0){
		JS_FLAG++;
		u1_buff[JS_FLAG-2] = receiveData;
		
		SBUF=u1_buff[JS_FLAG-2];//�����յ������ݷ��뵽���ͼĴ���
		while(!TI);			 //�ȴ������������
		TI=0;						 //���������ɱ�־λ
	}
	
	if(JS_FLAG==5)
		JS_FLAG = 0;
}

void cc(u16 addr)
{       
    ISP_CONTR = 0x81;  
    ISP_CMD   = 0x03;                
    ISP_ADDRL = addr;       
    ISP_ADDRH = addr>>8;      
//    EA =0;   
    ISP_TRIG = 0x46;          
    ISP_TRIG = 0xB9;         
    _nop_();
    Q0();
		delay(300);                                          
}

void xcx_f(u16 addr,u8 dat)
{
//	if(addr==0x2002&&LED2_flag==2){
//		LED2_flag=1;
//		xcx(0x2006,1);
//	}
//	else if(addr==0x2005&&LED2_flag==1){
//		LED2_flag=2;
//		xcx(0x2006,2);
//	}
//		xcx(addr,dat);
			cc(0x2000);
			xcx(0x2002,LED2_colour);
			xcx(0x2003,LED2_lightness);
			xcx(0x2004,LED2_speed);
			xcx(0x2005,LED2_mode);
			xcx(0x2006,LED2_flag);
}


void xcx(u16 addr,u8 dat)
{     
    ISP_CONTR = 0x81;                  
    ISP_CMD   = 0x02;              
    ISP_ADDRL = addr;        
    ISP_ADDRH = addr>>8;      
    ISP_DATA  = dat;         
//    EA = 0;
    ISP_TRIG = 0x46;         
    ISP_TRIG = 0xB9;         
    _nop_();
    Q0();      
		delay(300);   
	
}

u8 dcx(u16 addr)
{   
    u8 dat;
       
        ISP_CONTR = 0x81;                  
    ISP_CMD   = 0x01;        
    ISP_ADDRL = addr;         
    ISP_ADDRH = addr>>8;      
//    EA = 0;
    ISP_TRIG = 0x46;         
    ISP_TRIG = 0xB9;         
    _nop_();
    dat = ISP_DATA;                          
        Q0();                                                            
        return dat;
}

void Q0()
{
    ISP_CONTR = 0;            
    ISP_CMD   = 0;            
    ISP_TRIG  = 0;           
}


/*******************************************************************************
* ������         :UsartInit()
* ��������		   :���ô���
* ����           : ��
* ���         	 : ��
*******************************************************************************/
void UsartInit()
{
	SCON=0X50;			//����Ϊ������ʽ1
	TMOD=0X20;			//���ü�����������ʽ2
//	PCON=0X80;			//�����ʼӱ�
	TH1=0xfd;				//��������ʼֵ���ã�ע�Ⲩ������4800��
	TL1=0xfd;
	ES=1;						//�򿪽����ж�
	EA=1;						//�����ж�
	TR1=1;					//�򿪼�����
}

/*******************************************************************************
* �� �� ��         : delay
* ��������		   : ��ʱ������i=1ʱ����Լ��ʱ10us
*******************************************************************************/
void delay(u16 i)
{
	while(i--);	
}

void delay_1s(int i)
{
    unsigned char a,b,c;
	for(;i>0;i--)
		for(c=10;c>0;c--)
			for(b=221;b>0;b--)
					for(a=207;a>0;a--);
}