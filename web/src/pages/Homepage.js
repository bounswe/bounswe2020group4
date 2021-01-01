import React, { useEffect, useState } from 'react'
import Carousel from '../components/Carousel'
import { searchProducts } from '../services/products'

import SampleDealsBrand from '../images/sample-deals-brand.jpg'
import SampleDealsBrand2 from '../images/sample-deals-brand2.jpg'


const Homepage = () => {
	const [bestSellers, setBestSellers] = useState([])
	const [recommendations, setRecommendations] = useState([])

	//This is just used to populate best sellers with dummy data
	useEffect(async () => {
		const response = await searchProducts('Men')
		setBestSellers(response.slice(0, 10))
		setRecommendations(response.slice(11, 20))
	}, [])


	return (
		<div>
			<div className='p-5 deals container-fluid' style={{backgroundColor: 'var(--red-main)'}}>
				<div className='row'>
					<div className='col-md p-3'>
						<img className='rounded-lg img-fluid' src={SampleDealsBrand} alt='deal brand'/>
					</div>
					<div className='col-md p-3'>
						<img className='rounded-lg img-fluid' src={SampleDealsBrand2} alt='deal brand'/>
					</div>
				</div>
			</div>
			<div className='p-5'>
				<Carousel items={bestSellers} title="Best Sellers" />
			</div>
			<div className='p-5'>
				<Carousel items={recommendations} title="Recommendations" />
			</div>
		</div>
	)
}

export default Homepage