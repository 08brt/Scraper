CREATE TABLE mail
(
    id         SERIAL PRIMARY KEY,
    from_email VARCHAR(255)                        NOT NULL,
    to_emails  VARCHAR(1000)                       NOT NULL,
    subject    VARCHAR(1000)                       NOT NULL,
    body       VARCHAR(10000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE location
(
    id         SERIAL PRIMARY KEY,
    city       VARCHAR(50) UNIQUE NOT NULL,
    latitude   FLOAT              NOT NULL,
    longitude  FLOAT              NOT NULL,
    country    VARCHAR(255)       NOT NULL,
    iso2       VARCHAR(2)         NOT NULL,
    processed  BOOLEAN            NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scraped_business
(
    id            SERIAL PRIMARY KEY,
    place_id      VARCHAR(255) UNIQUE NOT NULL,
    keyword       VARCHAR(255)        NOT NULL,
    location      VARCHAR(255)        NOT NULL,
    name          VARCHAR(255)        NOT NULL,
    address       VARCHAR(255)        NOT NULL,
    phone_number  VARCHAR(20),        -- API States phone number can be NULL
    website       VARCHAR(2048),      -- SO WE CAN CONTACT AND PROPOSE CREATING A WEBSITE
    email_address VARCHAR(1000),
    status_type   VARCHAR(30)         NOT NULL,-- Use the enum type here
    error_message VARCHAR(10000),
    created_at    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE communication
(
    id                SERIAL PRIMARY KEY,
    scraped_store_id BIGSERIAL NOT NULL,
    mail_id           BIGSERIAL NOT NULL,
    contacted         BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_scraped_store FOREIGN KEY (scraped_store_id) REFERENCES scraped_business (id),
    CONSTRAINT fk_mail FOREIGN KEY (mail_id) REFERENCES mail (id)
);

CREATE TABLE email_template
(
    id            SERIAL PRIMARY KEY,
    template_type VARCHAR(50)        NOT NULL,
    subject       VARCHAR(100)       NOT NULL,
    template      VARCHAR(50000)     NOT NULL,
    created_at    TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert Email Template Data
INSERT INTO email_template (template_type, subject, template)
VALUES ('HEALTH_CHECK_PROMO', 'Optimize Your Garage Reports with Free 7-Day Vehicle HealthCheck for iOS!', '<!DOCTYPE html>
<html>
<head>
    <title>Vehicle HealthCheck Introduction</title>
</head>
<body>
    <p>Dear {Business Owner},</p>

    <p>I hope this email finds you well. I am excited to introduce you to <strong>Vehicle HealthCheck iOS</strong>, the cutting-edge all-in-one platform designed to transform your garage''s vehicle inspection process. Say goodbye to the outdated pen-and-paper system and embrace a more efficient, cloud-based solution.</p>

    <p>Check us out on <a href="http://www.health-check.uk">www.health-check.uk</a></p>

    <p>Vehicle HealthCheck offers a comprehensive range of features tailored to streamline your operations:</p>
    <ul>
        <li><strong>Create Unlimited Health Check Reports:</strong> Easily generate detailed reports for every vehicle.</li>
        <li><strong>Cloud Storage:</strong> Securely store all your reports in the cloud, accessible only by you and your team.</li>
        <li><strong>Multi-User Access:</strong> Allow your entire team to access reports simultaneously with a single login.</li>
        <li><strong>Effortless Report Export:</strong> Send reports directly to customers or backup copies to your email with just a few clicks.</li>
    </ul>

    <p>Our detailed Vehicle HealthCheck reports include essential information such as:</p>
    <ul>
        <li>Number Plate, Mileage, Time & Date</li>
        <li>Customer Contact Details</li>
        <li>Detailed checks on tyres, exterior lights, engine oil, coolant levels, and much more</li>
        <li>Customer and Technician Signatures</li>
    </ul>

    <h3>Why Choose Vehicle HealthCheck?</h3>
    <ul>
        <li><strong>Searchable Reports:</strong> Quickly locate reports by vehicle number plate or view a complete list of all stored reports.</li>
        <li><strong>Easy Export Options:</strong> Send vehicle reports to customers or back up copies to your private email effortlessly.</li>
        <li><strong>Affordable Pricing:</strong> At just £14.99 per month or £149.99 per year (our best value!), you get unlimited reports and dedicated customer support.</li>
        <li><strong>Special Offer: 7-Day Free Trial</strong></li>
    </ul>

    <p>We understand the importance of finding the right tool for your garage. That''s why we''re offering a 7-day free trial of Vehicle HealthCheck. Experience firsthand how our platform can enhance your workflow and improve customer satisfaction.</p>

    <p>To start your free trial, simply reply to this email and we''ll get it set up for you!</p>

    <p>Don''t miss this opportunity to modernize your garage operations with Vehicle HealthCheck. We look forward to helping you take your business to the next level.</p>

    <p>Best regards,</p>
    <p><strong>Bartosz Wieloch</strong><br>
    Owner of HealthCheck<br>
    +44 7714 723338</p>
</body>
</html>
');

INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hounslow', 51.4668, -0.375, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Birmingham', 52.48, -1.9025, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Portsmouth', 50.8058, -1.0872, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Southampton', 50.9025, -1.4042, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Nottingham', 52.9561, -1.1512, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bristol', 51.4536, -2.5975, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Manchester', 53.479, -2.2452, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Liverpool', 53.4094, -2.9785, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Leicester', 52.6344, -1.1319, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Worthing', 50.8147, -0.3714, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Coventry', 52.4081, -1.5106, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Belfast', 54.5964, -5.93, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bradford', 53.8, -1.75, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Derby', 52.9247, -1.478, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Plymouth', 50.3714, -4.1422, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Westminster', 51.4947, -0.1353, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wolverhampton', 52.5833, -2.1333, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Northampton', 52.2304, -0.8938, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Norwich', 52.6286, 1.2928, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Luton', 51.8783, -0.4147, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Solihull', 52.413, -1.778, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Islington', 51.544, -0.1027, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Aberdeen', 57.15, -2.11, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Croydon', 51.3727, -0.1099, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bournemouth', 50.72, -1.88, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Basildon', 51.58, 0.49, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Maidstone', 51.272, 0.529, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ilford', 51.5575, 0.0858, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Warrington', 53.39, -2.59, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Oxford', 51.75, -1.25, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Harrow', 51.5836, -0.3464, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('West Bromwich', 52.519, -1.995, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Gloucester', 51.8667, -2.25, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('York', 53.96, -1.08, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Blackpool', 53.8142, -3.0503, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Stockport', 53.4083, -2.1494, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Sale', 53.424, -2.322, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Tottenham', 51.5975, -0.0681, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Cambridge', 52.2053, 0.1192, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Romford', 51.5768, 0.1801, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Colchester', 51.8917, 0.903, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('High Wycombe', 51.6287, -0.7482, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Gateshead', 54.9556, -1.6, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Slough', 51.5084, -0.5881, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Blackburn', 53.748, -2.482, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Chelmsford', 51.73, 0.48, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rochdale', 53.61, -2.16, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rotherham', 53.43, -1.357, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Walthamstow', 51.584, -0.021, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Basingstoke', 51.2667, -1.0876, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Salford', 53.483, -2.2931, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wembley', 51.5528, -0.2979, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Worcester', 52.1911, -2.2206, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hammersmith', 51.4928, -0.2229, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rayleigh', 51.5864, 0.6049, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hemel Hempstead', 51.7526, -0.4692, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bath', 51.38, -2.36, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hayes', 51.5127, -0.4211, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Darlington', 54.527, -1.5526, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hove', 50.8352, -0.1758, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hastings', 50.85, 0.57, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Watford', 51.655, -0.3957, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Stevenage', 51.9017, -0.2019, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hartlepool', 54.69, -1.21, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Chester', 53.19, -2.89, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Fulham', 51.4828, -0.195, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Nuneaton', 52.523, -1.468, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ealing', 51.5175, -0.2988, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Aylesbury', 51.8168, -0.8124, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Edmonton', 51.6154, -0.0708, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Saint Albans', 51.755, -0.336, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Burnley', 53.789, -2.248, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Batley', 53.7167, -1.6356, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Scunthorpe', 53.5809, -0.6502, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Dudley', 52.508, -2.089, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Brixton', 51.4575, -0.1175, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Southall', 51.5111, -0.3756, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Paisley', 55.8456, -4.4239, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Chatham', 51.37, 0.52, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('East Ham', 51.5323, 0.0554, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Weston-super-Mare', 51.346, -2.977, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Carlisle', 54.8947, -2.9364, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('South Shields', 54.995, -1.43, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('East Kilbride', 55.7644, -4.1769, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Burton upon Trent', 52.8019, -1.6367, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Harrogate', 53.9919, -1.5378, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Crewe', 53.099, -2.44, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Lowestoft', 52.48, 1.75, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rugby', 52.37, -1.26, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Chingford', 51.623, 0.009, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Uxbridge', 51.5404, -0.4778, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Walsall', 52.58, -1.98, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Grays', 51.475, 0.33, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Walton upon Thames', 51.3868, -0.4133, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Thornton Heath', 51.4002, -0.1086, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Finchley', 51.599, -0.187, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Kensington', 51.5, -0.19, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Boston', 52.974, -0.0214, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Paignton', 50.4353, -3.5625, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Waterlooville', 50.88, -1.03, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Guiseley', 53.875, -1.706, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hornchurch', 51.5565, 0.2128, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Mitcham', 51.4009, -0.1517, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Feltham', 51.4496, -0.4089, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Stourbridge', 52.4575, -2.1479, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rochester', 51.375, 0.5, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Dewsbury', 53.691, -1.633, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Acton', 51.5135, -0.2707, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Twickenham', 51.449, -0.337, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wrecsam', 53.046, -2.993, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ellesmere Port', 53.279, -2.897, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bangor', 54.66, -5.67, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Taunton', 51.019, -3.1, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Loughborough', 52.7725, -1.2078, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Barking', 51.54, 0.08, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Edgware', 51.6185, -0.2729, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Littlehampton', 50.8094, -0.5409, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ruislip', 51.576, -0.433, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Streatham', 51.4279, -0.1235, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Royal Tunbridge Wells', 51.132, 0.263, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bebington', 53.35, -3.003, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Macclesfield', 53.25, -2.13, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wellingborough', 52.3028, -0.6944, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Kettering', 52.3931, -0.7229, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Braintree', 51.878, 0.55, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Royal Leamington Spa', 52.2919, -1.5358, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Barrow in Furness', 54.1108, -3.2261, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Dunfermline', 56.0719, -3.4393, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Altrincham', 53.3838, -2.3547, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Lancaster', 54.0489, -2.8014, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Crosby', 53.4872, -3.0343, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bootle', 53.4457, -2.9891, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Stratford', 51.5423, -0.0026, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Folkestone', 51.0792, 1.1794, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Cumbernauld', 55.945, -3.994, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Andover', 51.208, -1.48, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Neath', 51.66, -3.81, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rowley Regis', 52.488, -2.05, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Scarborough', 54.2825, -0.4, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Leith', 55.98, -3.17, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Yeovil', 50.9452, -2.637, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Eltham', 51.451, 0.052, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hampstead', 51.5541, -0.1744, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Sutton in Ashfield', 53.125, -1.261, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Morden', 51.4015, -0.1949, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Barnet', 51.6444, -0.1997, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Stretford', 53.4466, -2.3086, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Beckenham', 51.408, -0.022, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Greenford', 51.5299, -0.3488, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Cheshunt', 51.702, -0.035, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Kirkby', 53.48, -2.89, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Salisbury', 51.07, -1.79, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ashton', 53.4897, -2.0952, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Surbiton', 51.394, -0.307, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Castleford', 53.716, -1.356, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Catford', 51.4452, -0.0207, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Worksop', 53.3042, -1.1244, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Morley', 53.7492, -1.6023, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Merthyr Tudful', 51.743, -3.378, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Middleton', 53.555, -2.187, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Fleet', 51.2834, -0.8456, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Fareham', 50.85, -1.18, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Urmston', 53.4487, -2.3747, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Sutton', 51.3656, -0.1963, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Caerphilly', 51.578, -3.218, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bridgwater', 51.128, -2.993, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Newbury', 51.401, -1.323, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Welling', 51.4594, 0.1097, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Kingswood', 51.46, -2.505, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Dunstable', 51.886, -0.521, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ramsgate', 51.336, 1.416, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Strood', 51.393, 0.478, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Cleethorpes', 53.5533, -0.0215, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Pinner', 51.5932, -0.3894, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Great Yarmouth', 52.606, 1.729, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ilkeston', 52.9711, -1.3092, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Chorley', 53.653, -2.632, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Herne Bay', 51.37, 1.13, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bishops Stortford', 51.872, 0.1725, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Arnold', 53.005, -1.127, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Coalville', 52.724, -1.369, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bletchley', 51.994, -0.732, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Leighton Buzzard', 51.9165, -0.6617, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Airdrie', 55.86, -3.98, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Blyth', 55.126, -1.514, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Laindon', 51.574, 0.4181, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Llanelli', 51.684, -4.163, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Beeston', 52.927, -1.215, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Small Heath', 52.4629, -1.8542, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Whitley Bay', 55.0456, -1.4443, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Denton', 53.4554, -2.1122, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('West Bridgford', 52.932, -1.127, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Borehamwood', 51.6578, -0.2722, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Falkirk', 56.0011, -3.7835, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Walkden', 53.5239, -2.3991, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Kenton', 51.5878, -0.3086, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bridlington', 54.0819, -0.1923, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Billingham', 54.61, -1.27, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Grantham', 52.918, -0.638, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('North Shields', 55.0097, -1.4448, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hitchin', 51.947, -0.283, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Spalding', 52.7858, -0.1529, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rainham', 51.36, 0.61, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Letchworth', 51.978, -0.23, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wickford', 51.6114, 0.5207, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Huyton', 53.4111, -2.8403, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Abingdon', 51.6717, -1.2783, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Trowbridge', 51.32, -2.208, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wigston Magna', 52.5812, -1.093, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Didcot', 51.606, -1.241, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Earley', 51.433, -0.933, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bexleyheath', 51.459, 0.138, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ecclesfield', 53.4429, -1.4698, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Darwen', 53.698, -2.461, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Prestwich', 53.5333, -2.2833, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Pontypridd', 51.602, -3.342, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rutherglen', 55.828, -4.214, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Dover', 51.1295, 1.3089, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Chichester', 50.8365, -0.7792, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Deal', 51.2226, 1.4006, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bicester', 51.9, -1.15, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Northolt', 51.5467, -0.37, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wishaw', 55.7742, -3.9183, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Carshalton', 51.3652, -0.1676, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bulwell', 53.001, -1.197, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Newtownards', 54.591, -5.68, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Kendal', 54.326, -2.745, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Cramlington', 55.082, -1.585, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bromsgrove', 52.3353, -2.0579, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Pont-y-pŵl', 51.703, -3.041, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hanwell', 51.509, -0.338, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Frome', 51.2279, -2.3215, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wood Green', 51.5981, -0.1149, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Darlaston', 52.5708, -2.0457, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Ashington', 55.181, -1.568, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Longton', 52.9877, -2.1327, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Melton Mowbray', 52.7661, -0.886, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Aldridge', 52.606, -1.9179, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Farnworth', 53.5452, -2.3999, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Highbury', 51.552, -0.097, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Cheadle Hulme', 53.3761, -2.1897, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Newton Aycliffe', 54.62, -1.58, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bournville', 52.4299, -1.9355, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Shenley Brook End', 52.009, -0.789, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Consett', 54.85, -1.83, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Coulsdon', 51.3211, -0.1386, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bilston', 52.566, -2.0728, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Wellington', 52.7001, -2.5157, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bishop Auckland', 54.663, -1.676, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Longbridge', 52.395, -1.979, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Bloxwich', 52.614, -2.004, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Upminster', 51.5557, 0.2512, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Rhyl', 53.321, -3.48, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Droitwich', 52.267, -2.153, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Hindley', 53.5355, -2.5658, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Westhoughton', 53.549, -2.529, 'United Kingdom', 'GB');
INSERT INTO location (city, latitude, longitude, country, iso2)
VALUES ('Broadstairs', 51.3589, 1.4394, 'United Kingdom', 'GB');
