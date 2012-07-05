package mifosoffline;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.joda.time.LocalDate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;



@SuppressWarnings("deprecation")
public class RestController {

	private RestTemplate restTemplate;
	private String globalNumber;
        Logger logger=RestClientMain.logger;
        
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private static HttpEntity<String> prepareGet(MediaType type) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}
	
	
	
		
		//Mifos Post Request
			public Map postLoanRepayByGlobalNum(String globalNum,String amount,String trxnDate,int receiptId){
				
				Map responsebody=null;
                                
				try{
                                    
                                    	//This is for authentication logic
					CommonsClientHttpRequestFactory factory = (CommonsClientHttpRequestFactory) restTemplate.getRequestFactory();
					 HttpClient client = RestClientUtil.getClient();
					factory.setHttpClient(client);
					HttpEntity<String> entity = prepareGet(MediaType.APPLICATION_JSON);
					MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();			
					restTemplate.getMessageConverters().add(converter);
					String urlBase=RestClientUtil.URL;
					String finalurl=urlBase+"/account/loan/num-"+globalNum+"/repay.json?amount="+amount+"&&paymentDate="+trxnDate+"&&paymentModeId=1"+"&&receiptId="+receiptId;
                                        LocalDate date=new LocalDate();
                                        String todayDate=date.getDayOfMonth()+"-"+date.getMonthOfYear()+"-"+date.getYear();
                                        if(trxnDate.equalsIgnoreCase(todayDate)){
					//this is actual REST call to the server
					ResponseEntity<Map> postResponse = restTemplate.exchange(finalurl,HttpMethod.POST, entity, Map.class);
					responsebody=postResponse.getBody();
                                        //System.out.println("resonse body contains.."+responsebody);
					if(responsebody!=null){
                                           // System.out.println("Status:"+responsebody.get("status")+"\tcause:"+responsebody.get("cause"));
                                        if(responsebody.get("status")!=null){
                                            if("error".equals(responsebody.get("status"))){
						System.out.println("----------");
                                                System.out.println("Error      : loan payment is not succeed");
                                                logger.error("loan payment is not succeed");
						if(responsebody.get("cause")!=null){
                                                    System.out.println("Root Cause : "+responsebody.get("cause"));
                                                }
						
                                                else{
                                                    System.out.println("Root Cause : loan Account is not valid");						
                                                    System.out.println("----------");
                                                    System.out.println("The message is.."+responsebody.get("message"));
                                                }
                                                    
					}
                                            }
                                            else {
                                                
                                                System.out.println("Invalid amount entered");
                                                
                                                                                          
                                            }
                                        }
                                        else{
                                            System.out.println("Invalid username/password");
                                        }
                                        }
                                        else{
                                            System.out.println("Date comparison failed in Loan Account");
                                        }
				}catch (Exception e){
					e.printStackTrace();			
                                    System.out.println(e);
				}
                                
                                
				return responsebody;

			}
			
			//Mifos Post Request
				public Map postSavingDepositByGlobalAccountNum(String globalNum,String amount,String trxnDate,String paymentTypeId,int receiptId){
					
					Map responsebody=null;
					try{
						//This is for authentication logic
						CommonsClientHttpRequestFactory factory = (CommonsClientHttpRequestFactory) restTemplate.getRequestFactory();
						HttpClient client = RestClientUtil.getClient();
						factory.setHttpClient(client);
						
						HttpEntity<String> entity = prepareGet(MediaType.APPLICATION_JSON);
						MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();			
						restTemplate.getMessageConverters().add(converter);
                        
						String urlBase=RestClientUtil.URL;
						String finalurl=urlBase+"/account/savings/num-"+globalNum+"/deposit.json?amount="+amount+"&&trxnDate="+trxnDate+"&&paymentTypeId="+paymentTypeId+"&&receiptId="+receiptId;
                                                LocalDate date=new LocalDate();
                                                String todayDate=date.getDayOfMonth()+"-"+date.getMonthOfYear()+"-"+date.getYear();
                                                if(trxnDate.equalsIgnoreCase(todayDate)){

						//this is actual REST call to the server
						ResponseEntity<Map> postResponse = restTemplate.exchange(finalurl,HttpMethod.POST, entity, Map.class);
						responsebody=postResponse.getBody();
					    if(responsebody!=null){
                                                if("error".equals(responsebody.get("status"))){
							System.out.println("----------");
							System.out.println("Error      : saving payment is not succeed");
                                                        logger.error("saving payment is not succeed");
							System.out.println("Root Cause : "+responsebody.get("cause"));
							System.out.println("----------");
						}
                                            }
                                                else {
                                                    System.out.println("Invalid username/password");
                                                }
                                                }
                                                else{
                                            System.out.println("Date comparison failed in Savings Account");
                                        }
					}catch (Exception e){
						e.printStackTrace();			
					}
					return responsebody;

				}
			
}
