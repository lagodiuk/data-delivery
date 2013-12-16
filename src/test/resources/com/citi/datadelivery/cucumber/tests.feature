Feature: Process messages

Scenario: Filtering rows by age
 
	Given following input rows
	|	40378	|Sandefurthe, Leonard	|1		|741 Uncle Way				|Coil		|31551-4167	|
	|	19956	|Heath, Mauglen			|68		|209 Crow Parkway			|Mailbox	|62408-0407	|
	|	41150	|Yong, Rauffe			|1		|42 Cloth Avenue			|Coil		|31551-4167	|
	|	6373	|Clewghe, Rebacca		|44		|280 Crayon Circle			|Cracker	|08133-6786	|
	|	40411	|Fewler, Mathew			|99		|472 Uncle Way				|Coil		|31551-4167	|
	|	14909	|Surteis, Bryan			|42		|684 Sink 					|Cattle		|80638-8441	|
	|	42334	|Sighwicke, Rebacca		|64		|430 Crib Circle			|Coil		|78243-2027	|
	|	16968	|Jollye, Reginolde		|104	|531 Morning Highway		|Cattle		|73635-0651	|
	
	When filtering messages by age of 50
	
	Then following filtered rows are expected
	|	19956	|Heath, Mauglen			|68		|209 Crow Parkway			|Mailbox	|62408-0407	|
	|	40411	|Fewler, Mathew			|99		|472 Uncle Way				|Coil		|31551-4167	|
	|	42334	|Sighwicke, Rebacca		|64		|430 Crib Circle			|Coil		|78243-2027	|
	|	16968	|Jollye, Reginolde		|104	|531 Morning Highway		|Cattle		|73635-0651	|
	
	
Scenario: Filtering rows by city
 
	Given following input rows
	|	40378	|Sandefurthe, Leonard	|1		|741 Uncle Way				|Coil		|31551-4167	|
	|	19956	|Heath, Mauglen			|68		|209 Crow Parkway			|Mailbox	|62408-0407	|
	|	41150	|Yong, Rauffe			|1		|42 Cloth Avenue			|Coil		|31551-4167	|
	|	6373	|Clewghe, Rebacca		|44		|280 Crayon Circle			|Cracker	|08133-6786	|
	|	40411	|Fewler, Mathew			|99		|472 Uncle Way				|Coil		|31551-4167	|
	|	14909	|Surteis, Bryan			|42		|684 Sink 					|Cattle		|80638-8441	|
	|	42334	|Sighwicke, Rebacca		|64		|430 Crib Circle			|Coil		|78243-2027	|
	|	16968	|Jollye, Reginolde		|104	|531 Morning Highway		|Cattle		|73635-0651	|
	
	When filtering messages by city of "Coil"
	
	Then following filtered rows are expected
	|	40378	|Leonard Sandefurthe	|1		|741 Uncle Way				|Coil		|31551-4167	|
	|	41150	|Rauffe Yong			|1		|42 Cloth Avenue			|Coil		|31551-4167	|
	|	40411	|Mathew Fewler			|99		|472 Uncle Way				|Coil		|31551-4167	|
	|	42334	|Rebacca Sighwicke 		|64		|430 Crib Circle			|Coil		|78243-2027	|
	
	
Scenario: Calculating average age
 
	Given following input rows
	|	40378	|Sandefurthe, Leonard	|1		|741 Uncle Way				|Coil		|31551-4167	|
	|	19956	|Heath, Mauglen			|68		|209 Crow Parkway			|Mailbox	|62408-0407	|
	|	41150	|Yong, Rauffe			|1		|42 Cloth Avenue			|Coil		|31551-4167	|
	|	6373	|Clewghe, Rebacca		|44		|280 Crayon Circle			|Cracker	|08133-6786	|
	|	40411	|Fewler, Mathew			|99		|472 Uncle Way				|Coil		|31551-4167	|
	|	14909	|Surteis, Bryan			|42		|684 Sink 					|Cattle		|80638-8441	|
	|	42334	|Sighwicke, Rebacca		|64		|430 Crib Circle			|Coil		|78243-2027	|
	|	16968	|Jollye, Reginolde		|104	|531 Morning Highway		|Cattle		|73635-0651	|
	
	When calculating average age
	
	Then average age must be 52.875 and number of processed messages must be 8
 