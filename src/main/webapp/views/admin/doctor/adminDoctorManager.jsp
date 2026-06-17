<%@page import="com.hospital.common.dto.DoctorDTO"%>
<%@page import="com.hospital.doctor.DoctorDAO"%>
<%@page import="com.hospital.admin.doctor.AdminDoctorDAO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorSearchDTO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorFormDTO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO"%>
<%@page import="com.hospital.common.dto.DoctorStatusDTO"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="adminMenu" value="reservation" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 공통 레이아웃 테스트</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>

<!-- jQuery google API -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

	<style type="text/css">
       /*  #tabDeptInven {
            border: 1px solid #000;
        }/* deptInven */ 

        /* #tabDeptInven > tbody > tr > td {
            text-align: left;
            border: 1px solid #333;
        }

        #tabDeptInven > thead > tr > th, #tabDeptInven > tbody > tr > th {
            border-bottom: 1px solid #000;
            text-align: center;
        }
 */		
 		#tabDeptInven {
 			text-align: center;
 			vertical-align: center;
 		}
 		[name="btnTd"] {
 			text-align: right;
 		}
 		tbody > tr > td {
 			height: 60px;
 		}
 		
		.radio-center {
			text-align: center;
		}
		
        .admin-view-area {
            margin: 20px;
            position: relative;
        }/* admin-view-area */
    </style>
    
    <script type="text/javascript">
        $(function(){
        	<% 
			//JSON 데이터 저장
			//진료과 데이터
			List <DepartmentDTO> list = new ArrayList <DepartmentDTO>();
			list.add(new DepartmentDTO("DP001","치과","치아를 관리하는 과","Y","본관1층"));
			list.add(new DepartmentDTO("DP002","산부인과","임산부를 관리해주는 과","Y","본관2층"));
			list.add(new DepartmentDTO("DP003","안과","눈을 관리해주는 과","Y","별관2층"));
			list.add(new DepartmentDTO("DP004","외과","외과인데 사용 안한다요","N",""));
			
			List <DoctorStatusDTO> statusList = new ArrayList<DoctorStatusDTO>();
			statusList.add(new DoctorStatusDTO("CLS", "휴진"));
			statusList.add(new DoctorStatusDTO("MT", "진료중"));
			statusList.add(new DoctorStatusDTO("SRG", "수술"));
			
			List<DoctorDTO> doctorDTOList = new ArrayList<DoctorDTO>();
			doctorDTOList.add(new DoctorDTO(123456,"DP001","의사1","010-1234-5678","CRF","사랑니","사랑니는 잘못 나면 괴롭다","c:/image/thum.png","c:/image/tedail_image.png","2026-06-01","충치, 사랑니","MT"));	
			
			%>
        });
    </script>
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css' />">
</head>
<body>

<jsp:include page="/views/common/adminHeader.jsp" />

<div class="admin-layout">
    <jsp:include page="/views/common/adminSidebar.jsp" />

    <main class="admin-content">
        <div class="admin-page-title">
            <h2>의료진 관리</h2>
        </div>

        <section class="admin-card">
            <form class="admin-search-area">
                <div class="admin-view-area">
                    <table id="tabSearch">
                    	<tr>
                    		<td style="width: 90px;">진료과</td>
                    		<td style="width: 120px;">
	                    		<select id="selectDeptName">
	                    			<%
	                    			for(int i=0; i < list.size(); i++){
	                    			%>
	                    				<option value="<%= list.get(i).getDeptNo() %>"><%= list.get(i).getDeptName() %></option>
	                    			<%
	                    			}// end for
	                    			%>
	                    		</select>
                    		</td>
                    		<td style="width: 120px;">
                    			<select id="selectDeptStatus">
                    				<%
                    				for(int i=0; i < statusList.size(); i++){
                    				%>
                    					<option value="<%= statusList.get(i).getStatusCode() %>"><%= statusList.get(i).getStatusName() %></option>
                    				<%
                    				}// end for
                    				%>
                    			</select>
                    		</td>
                    		<td style="width: 90px;">이름</td>
                    		<td style="width: 150px;"><input type="text" id="txtSearchName" placeholder="의사 이름 입력"></td>
                    		<td style="width: 150px;"><input type="button" id="btnSearch" value="검색"></td>
                    		<td style="width: 1200px;"><input type="button" id="btnAddDoctor" value="의료진 등록"></td>
                    	</tr>
                    </table>
                </div>
            </form>
            <form class="admin-search-area">
            	<div class="admin-view-area">
            		<table>
            			<thead>
	            			<tr>
		            			<th>번호</th>
		            			<th>이름</th>
		            			<th>진료과</th>
		            			<th>직급</th>
		            			<th>상태</th>
	            			</tr>
            			</thead>
            			<tbody>
            				<%
            				String dortorNameTemp="";
            				String doctorDeptTemp="";
            				String doctorPosition="";
           					for(int i=0; i<doctorDTOList.size(); i++){
           						dortorNameTemp=doctorDTOList.get(i).getName();
           						doctorDeptTemp=doctorDTOList.get(i).getDeptNo();
           						doctorPosition=doctorDTOList.get(i).getPositionCode();
            				%>
            				<tr>
            					<td><%= i %><% //번호 %></td>
            					<td><%= dortorNameTemp %><% //이름 %></td>
            					<td><%= doctorDeptTemp %><% //진료과 %></td>
            					<td><%= doctorPosition %><% //직급 %>></td>
            					<td>
            						<select>
            							<%
            							String sel = "";
	                    				for(int j=0; j < statusList.size(); j++){
	                    					if(doctorDTOList.get(i).getStatusCode() == statusList.get(j).getStatusCode()){
	                    						sel=" selected='selected'";
	                    					} else {
	                    						sel = "";
	                    					}// end else if
	                    				%>
	                    					<option value="<%= statusList.get(j).getStatusCode() %><%= sel %>"><%= statusList.get(j).getStatusName() %></option>
	                    				<%
	                    				}// end for
	                    				%>
            						</select>
            					</td>
            				</tr>
            				<%
           					}// end for
            				%>
            			</tbody>
            		</table>
            	</div>
            </form>
        </section>
    </main>
</div>

<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>