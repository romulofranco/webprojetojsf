package br.com.ifsul.fsi.util;

import br.com.ifsul.fsi.util.Props;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class Util implements Serializable {
	public static final String DIAS = "d";
	public static final String MESES = "m";
	public static final String ANOS = "a";

	public static String[] DAYWEEK = { "", "dom", "seg", "ter", "qua", "qui", "sex", "sab" };

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(Util.class);

	private Timestamp t1;
	private Timestamp t2;
	private String label;

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	static SecureRandom rnd = new SecureRandom();

	public void initialTimestamp(String label) {
		this.t1 = new Timestamp(System.currentTimeMillis());
		this.label = label;
	}

	public static File newFile(File file, String detail) {
		return new File(file.getParentFile(),
				file.getName().substring(0, file.getName().indexOf(".")) + "-" + detail + ".png");

	}

	public static String getDayWeek(int dia) {
		return DAYWEEK[dia];
	}

	public static BufferedImage createClipped(BufferedImage input) {
		int t = Math.min(input.getWidth(), input.getHeight());
		BufferedImage other = new BufferedImage(t, t, input.TYPE_INT_ARGB);
		// Obtém o contexto gráfico
		Graphics2D g2d = other.createGraphics();
		// Define a área de pintura para um círculo
		g2d.setClip(new Ellipse2D.Double(0, 0, t, t));
		// Desenha a imagem
		g2d.drawImage(input, 0, 0, null);
		// Libera o contexto gráfico
		g2d.dispose();
		return other;
	}

	public String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}

	public void finalTimestamp() {
		this.t2 = new Timestamp(System.currentTimeMillis());

		long total = this.t2.getTime() - this.t1.getTime();

		logger.debug("Total for " + this.label + " - " + (total) + " milliseconds");

	}

	static public boolean checaCPF(String strCpf) {
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		strCpf = strCpf.replace('.', ' ');// onde há ponto coloca espaço
		strCpf = strCpf.replace('/', ' ');// onde há barra coloca espaço
		strCpf = strCpf.replace('-', ' ');// onde há traço coloca espaço
		strCpf = strCpf.replaceAll(" ", "");// retira espaço

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por
			// diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro digito
			// calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o
		// resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o
		// resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
		return nDigVerific.equals(nDigResult);
	}

	public static boolean checaCNPJ(String cnpj) {
		if (!cnpj.substring(0, 1).equals("")) {
			try {
				cnpj = cnpj.replace('.', ' ');// onde há ponto coloca espaço
				cnpj = cnpj.replace('/', ' ');// onde há barra coloca espaço
				cnpj = cnpj.replace('-', ' ');// onde há traço coloca espaço
				cnpj = cnpj.replaceAll(" ", "");// retira espaço
				int soma = 0, dig;
				String cnpj_calc = cnpj.substring(0, 12);
				if (cnpj.length() != 14) {
					return false;
				}
				char[] chr_cnpj = cnpj.toCharArray();
				/* Primeira parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				/* Segunda parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				return cnpj.equals(cnpj_calc);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static Date converteStringToDate(String dataStr) {
		try {
			String pattern = (dataStr.length() > 10 ? "dd/MM/yyyy HH:mm:ss" : "dd/MM/yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(dataStr);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static Date converteStringToDate(String dataStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(dataStr);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static String getMes(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM");
		return sdf.format(data);
	}

	public static String getMes(Date data, boolean mesNumerico) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(data);
	}

	public static String getFullDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD_HHmm");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	public static double[] convertLatLong(String strLatlong) throws NumberFormatException {
		double[] latlong = { 0, 0 };

		if (!strLatlong.isEmpty()) {
			String[] coords = strLatlong.split(",");
			latlong[0] = Double.parseDouble(coords[0].trim());
			latlong[1] = Double.parseDouble(coords[1].trim());
		}

		return latlong;

	}

	public static Timestamp convertTOUTC(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		calendar.setTimeInMillis(timestamp * 1000);

		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		return new Timestamp(calendar.getTimeInMillis());
	}

	public static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static String getMenosMes(int mes) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		Date data = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.MONTH, -mes);
		String datResult = sdf.format(c.getTime());
		return datResult;
	}

	public static String getMesAno() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		Date data = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		String datResult = sdf.format(c.getTime());
		return datResult;
	}

	public static double getHoras(Date dtInicio, Date dtFim) {
		// logger.debug("MS dat1 dat2: " + dtInicio.getTime() + " - " +
		// dtFim.getTime());

		long ms = dtFim.getTime() - dtInicio.getTime();
		long segundos = (ms / 1000) % 60; // se não precisar de segundos, basta remover esta linha.
		long minutos = (ms / 60000) % 60;// 60000 = 60 * 1000
		long horas = ms / 3600000; // 3600000 = 60 * 60 * 1000
		float total = horas + (minutos * (float) 0.0166666666666667);
		return total;

	}

	public static long calcularDiferencaDatas(Date dataInicio, Date dataFim, String tipo) {
		long result = 0;

		if (dataInicio == null || dataFim == null) {
			return 0;
		}

		if (tipo.startsWith(Util.DIAS)) {
			result = getDias(dataInicio, dataFim);
		} else if (tipo.startsWith(Util.MESES)) {
			result = getMeses(dataInicio, dataFim);
		} else if (tipo.startsWith(Util.ANOS)) {
			result = getAnos(dataInicio, dataFim);
		}

		return result;
	}

	public static long getDias(Date dtInicio, Date dtFim) {
		long diff = dtFim.getTime() - dtInicio.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public static long getAnos(Date dataInicio, Date dataFim) {
		Calendar dtInicio = new GregorianCalendar();
		Calendar dtFim = new GregorianCalendar();

		dtInicio.setTime(dataInicio);
		dtFim.setTime(dataFim);

		int yearsInBetween = dtFim.get(Calendar.YEAR) - dtInicio.get(Calendar.YEAR);
		// int monthsDiff = dtFim.get(Calendar.MONTH) - dtInicio.get(Calendar.MONTH);
		// long months = yearsInBetween * 12 + monthsDiff;

		return yearsInBetween;
	}

	public static long getMeses(Date dataInicio, Date dataFim) {
		Calendar dtInicio = new GregorianCalendar();
		Calendar dtFim = new GregorianCalendar();

		dtInicio.setTime(dataInicio);
		dtFim.setTime(dataFim);

		int yearsInBetween = dtFim.get(Calendar.YEAR) - dtInicio.get(Calendar.YEAR);
		int monthsDiff = dtFim.get(Calendar.MONTH) - dtInicio.get(Calendar.MONTH);
		long months = yearsInBetween * 12 + monthsDiff;

		return months;
	}

	public static double arredondar(double valor, int casas, int ceilOrFloor) {
		double arredondado = valor;
		arredondado *= (Math.pow(10, casas));
		if (ceilOrFloor == 0) {
			arredondado = Math.ceil(arredondado);
		} else {
			arredondado = Math.floor(arredondado);
		}
		arredondado /= (Math.pow(10, casas));
		return arredondado;
	}

	public static String convertDateToStr(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = df.format(date);
		return reportDate;
	}

	public static Date somarDiasDataStr(Date data) {
		if (data == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH) + 1;
		int ano = cal.get(Calendar.YEAR);

		if (mes == 12) {
			mes = 1;
			ano = ano + 1;
		} else {
			mes = mes + 1;
		}

		if (mes == 2 && dia > 28) {
			dia = 28;
		}

		String result = "" + dia;
		if (result.length() == 1) {
			result = "0" + dia;
		}

		String month = "" + mes;
		if (month.length() == 1) {
			result = result + "/0" + month;
		} else {
			result = result + "/" + month;
		}

		result = result + "/" + ano;
		Date retorno = Util.converteStringToDate(result);
		return retorno;
	}

	public static Date somarDiasData(int dias, Date vencimento) {
		Calendar dataInicio = new GregorianCalendar();
		dataInicio.setTime(vencimento);
		dataInicio.add(Calendar.DATE, +dias);
		return dataInicio.getTime();
	}

	public static String getAnoAtual() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		return yearInString;
	}

	public static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static Date startOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 0);
		dCal.set(Calendar.MINUTE, 0);
		dCal.set(Calendar.SECOND, 0);
		dCal.set(Calendar.MILLISECOND, 0);

		return dCal.getTime();
	}

	public static void writeLogFile(String username, String log, Integer diag) {
		try {
			String path = Props.getInstance().getConfig("docs") + "/logs/";
			String diagFile = diag == 1 ? "DIAG_" : "LOG_";
			String filename = path + diagFile + username + "_" + getFullDate() + ".log";
			File arquivo = new File(filename);
			FileWriter inserindo;
			inserindo = new FileWriter(arquivo, true);
			inserindo.write("Arquivo de Log para: " + username + " Data: "
					+ new Date(System.currentTimeMillis()).toString() + "\n\r");
			inserindo.write(log);
			inserindo.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	public static void sendEmail(String to, String subject, String mailBody, boolean html) {

		try {
			String server = Props.getInstance().getConfig("mail.smtp.host");
			String porta = Props.getInstance().getConfig("mail.smtp.port");
			String username = Props.getInstance().getConfig("mail.username");
			String pwd = Props.getInstance().getConfig("mail.password");

			Properties props = new Properties();
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", porta);

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, pwd);
				}
			});

			/** Ativa Debug para sessão */
			session.setDebug(true);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username)); // Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(to);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(subject);// Assunto

			if (html) {
				MimeMultipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(mailBody, "text/html");
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			} else {
				message.setText(mailBody);
			}

			Transport.send(message);

			logger.info("Email enviado com sucesso!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void aguardar(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static JSONObject getGeoPointFromAddress(String locationAddress) {

		HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" + locationAddress
				+ "&region=it&language=it&sensor=false?key=AIzaSyD8QsBkB9ZIQOvUhvYdOlQ0x64mTVwu6Os");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
			logger.info("Google Geocoding Response" + stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

	public static void main(String[] args) {
		logger.info("" + Util.getGeoPointFromAddress("Rua das Violetas"));
	}

}
