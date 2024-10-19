package com.map.matrimonytest.mock

import com.map.matrimonytest.R
import com.map.matrimonytest.db.entity.ProfileEntity


class ProfileDetails {
    companion object {
         var profileDetails = mutableListOf<ProfileEntity>()

        fun getDetails() {
            profileDetails.add(ProfileEntity(
            id = 1,
            name ="Priyanka",
            age = 27,
            height = "5ft 1 in",
            profession = "Software Engineer",
            location = "Chennai",
            imageResId = R.drawable.ic_profile_photo1,
            caste = "Tamil, Nair",
            state = "Tamil Nadu",
            country = "India",
            zodiac = "Poosam",
            imageIds = "${R.drawable.ic_profile_photo1},${R.drawable.ic_profile_photo4}",
            isDailyRecommendation = false
            ))

            profileDetails.add(ProfileEntity(
                id = 2,
                name ="Ayishwarya",
                age = 26,
                height = "5ft 1 in",
                profession = "MBBS, Doctor",
                location = "Chennai",
                imageResId = R.drawable.ic_profile_photo2,
                caste = "Tamil, Nair",
                state = "Tamil Nadu",
                country = "India",
                zodiac = "Leo",
                imageIds = "${R.drawable.ic_profile_photo2},${R.drawable.ic_profile_photo5}",
                isDailyRecommendation = false
            ))

            profileDetails.add(ProfileEntity(
                id = 3,
                name ="Divya",
                age = 27,
                height = "5ft 1 in",
                profession = "Business Analyst",
                location = "Chennai",
                imageResId = R.drawable.ic_profile_photo3,
                caste = "Tamil, Nair",
                state = "Tamil Nadu",
                country = "India",
                zodiac = "Poosam",
                imageIds = "${R.drawable.ic_profile_photo3},${R.drawable.ic_profile_photo6}",
                isDailyRecommendation = false
            ))


            profileDetails.add(ProfileEntity(
                id = 4,
                name ="Priya",
                age = 26,
                height = "5ft 1 in",
                profession = "Software Engineer",
                location = "Chennai",
                imageResId = R.drawable.ic_profile_photo7,
                caste = "Tamil, Nair",
                state = "Tamil Nadu",
                country = "India",
                zodiac = "Poosam",
                imageIds = "${R.drawable.ic_profile_photo7},${R.drawable.ic_profile_photo8}",
                isDailyRecommendation = false
            ))

            profileDetails.add(ProfileEntity(
                id = 5,
                name ="Janani",
                age = 25,
                height = "5ft 1 in",
                profession = "MBBS, Doctor",
                location = "Chennai",
                imageResId = R.drawable.ic_profile_photo9,
                caste = "Tamil, Nair",
                state = "Tamil Nadu",
                country = "India",
                zodiac = "Leo",
                imageIds = "${R.drawable.ic_profile_photo9},${R.drawable.ic_profile_photo10}",
                isDailyRecommendation = false
            ))

            profileDetails.add(ProfileEntity(
                id = 6,
                name ="Nandhini",
                age = 25,
                height = "5ft 1 in",
                profession = "Senior Software Engineer",
                location = "Chennai",
                imageResId = R.drawable.ic_profile_photo12,
                caste = "Tamil, Nair",
                state = "Tamil Nadu",
                country = "India",
                zodiac = "Leo",
                imageIds = "${R.drawable.ic_profile_photo12},",
                isDailyRecommendation = true
            ))

            profileDetails.add(ProfileEntity(
                id = 7,
                name ="Nadhiya",
                age = 27,
                height = "5ft 1 in",
                profession = "Hiring Manager",
                location = "Chennai",
                imageResId = R.drawable.ic_profile_photo13,
                caste = "Tamil, Nair",
                state = "Tamil Nadu",
                country = "India",
                zodiac = "Leo",
                imageIds = "${R.drawable.ic_profile_photo13},",
                isDailyRecommendation = true
            ))
        }
    }
}